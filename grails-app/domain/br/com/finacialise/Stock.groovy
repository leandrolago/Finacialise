package br.com.finacialise


import org.bson.types.ObjectId;
import org.eclipse.jdt.internal.compiler.util.Util.Displayable;


class Stock {
		
	static{
		Stock.metaClass.getId = { -> stockCode }	
		Stock.metaClass.setId = { id -> stockCode = id }
		
	}

	String name
	
	String stockCode
	
	List<StockPrice> prices = []
		
	//static embedded = ['prices']
	
	static transients = ['id']

	static hasMany = [prices: StockPrice]
	
	static mapping = {
		version false
	}
	
    static constraints = {
		id ( generator:"assigned", name : 'stockCode', column: 'stockCode', type: 'string' )
    }
	
	public String toString(){
		return stockCode	
	}
	

}
