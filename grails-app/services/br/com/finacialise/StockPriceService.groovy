package br.com.finacialise

import org.springframework.transaction.annotation.Transactional;


class StockPriceService {

	def grailsApplication
	def stockPriceUrl
	
	def updateAllPrices(){
		Stock.findAll().each {
			updatePrices(it);	
		}
	}
	
	@Transactional()
    def updatePrices( Stock stock ) {
		stockPriceUrl = stockPriceUrl ? stockPriceUrl : grailsApplication.config.stockPriceUrl
		new URL(stockPriceUrl.replace("#{stockCode}", stock.stockCode)).openStream()
		.eachLine{ line, number-> 
			if ( number > 1 ) {
				def split = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*\$)")
				def price = new StockPrice( 
					day: new Date().parse("d/M/yyyy", split[0] ),
					price: split[1].replace("\"", "").replace(",",".")?: "0" as BigDecimal,
					variation: split[2].replace("\"", "").replace(",",".")?: "0" as Double,
					volume: split[3].replace("\"", "").replace(",",".")?: "0" as Double,
					stock: stock
				)
				if ( !stock.prices.contains( price ) ){
					stock.prices << price
				}
			}	
		}
		stock.save()
    }
}
