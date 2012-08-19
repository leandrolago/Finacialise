package br.com.finacialise;

import grails.test.mixin.*;

import org.junit.Test

@TestFor(StockInfoService)
@Mock([ Stock ])
class StockInfoServiceTests {
		
	@Test
	void gatherStocksTest(){
		service.bmfStockInfoUrl = "file:test/resources/BM_FBOVESPA.html"
		List<Stock> stocks =  service.gatherStocks();
		assertNotNull("Nenhuma Stock retornada" , stocks )
		assertEquals( 80, stocks.size )
		
	}

}
