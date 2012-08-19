package br.com.finacialise

import org.springframework.transaction.annotation.Transactional;

class StockInfoService {
	
	def grailsApplication
	def bmfStockInfoUrl
	def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
	def slurper = new XmlSlurper(tagsoupParser)
	
	
	@Transactional()
	List<Stock> gatherStocks(){
		
		bmfStockInfoUrl = grailsApplication.config.bmfStockInfoUrl
		
		def page = new URL(bmfStockInfoUrl).getText();
		
		def html = slurper.parseText(page)
		
		List<Stock> stocks = []
		
		html.body.'**'.findAll{ it.@id == 'ctl00_contentPlaceHolderConteudo_grdFundoImobiliario_ctl01' }
		.tbody.tr[0].each {  
			
			def stock = new Stock( name:it.td[0].text(), stockCode : it.td[2].text(), url: "http://www.bmfbovespa.com.br/renda-variavel/${it.td[0].a.@href.text()}")
			//stock.id = it.td[2].text()
			stock.save()
			stocks << stock
		}
		
		return stocks
	}

}
