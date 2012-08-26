package br.com.finacialise

import groovy.transform.EqualsAndHashCode;
import org.bson.types.ObjectId

@EqualsAndHashCode( includeFields=false, includes="day" )
class StockPrice {
	
	ObjectId id;
	
	Date day;
	
	BigDecimal price;
	
	Double variation;
	
	Double volume;
	
	static belongsTo = [stock: Stock]
	

	static mapping = {
		version false
	}
	
	public String toString(){
		return String.format('%tF', day)	
	}

}
