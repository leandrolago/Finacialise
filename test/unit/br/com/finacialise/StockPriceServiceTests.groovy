package br.com.finacialise



import grails.test.mixin.*
//import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(StockPriceService)
@Mock([ Stock ])
class StockPriceServiceTests {

    void testUpdatePrices() {
		Stock stock = new Stock( name:"teste", stockCode:"testeStock" )
		
		service.stockPriceUrl = "file:test/resources/#{stockCode}/1m.csv"
		
        service.updatePrices( stock )
		
		assertNotNull( "Nenhum preço importado", stock.prices )
		assertEquals( 21, stock.prices.size )
    }
	
	void testUpdatePricesWithSomePrices() {
		Stock stock = new Stock( name:"teste", stockCode:"testeStock" )
		def price = new StockPrice( day: new Date().parse("d/M/yyyy", "23/08/2012" ), price: 50.0, variation: 0, volume: 0 )
		
		stock.prices << price
		
		service.stockPriceUrl = "file:test/resources/#{stockCode}/1m.csv"
		
		service.updatePrices( stock )
		
		assertNotNull( "Nenhum preço importado", stock.prices )
		assertEquals( 21, stock.prices.size )
		assertEquals( stock.prices[0].day, price.day )
		assertEquals( stock.prices[0].price, price.price)
		assertEquals( stock.prices[0].variation, price.variation, 0)
		assertEquals( stock.prices[0].volume, price.volume, 0)
	}
	
	void testUpdateAllPrices(){
		mockDomain( Stock, [
			[name:"teste", stockCode:"testeStock"],
			[name:"teste2", stockCode:"testeStock2"] ] )
		
		service.stockPriceUrl = "file:test/resources/#{stockCode}/1m.csv"
		
		service.updateAllPrices()
		
		Stock.findAll().each {
			assertEquals( 21, it.prices.size )
		}
	}
}
