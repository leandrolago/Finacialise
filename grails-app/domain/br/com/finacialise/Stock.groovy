package br.com.finacialise

import org.bson.types.ObjectId;


class Stock {
		
	static{
		Stock.metaClass.getId = { -> stockCode }	
	}

	String name
	
	String stockCode
	
	static transients = ['id']

		static mapping = {
		version false
	}
	
    static constraints = {
		id generator:"assigned", name : 'stockCode', column: 'stockCode', type: 'string'
    }
}
