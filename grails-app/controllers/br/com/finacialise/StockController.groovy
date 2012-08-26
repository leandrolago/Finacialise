package br.com.finacialise

import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException

class StockController {
	

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def scaffold = true

	StockInfoService stockInfoService
	
	StockPriceService stockPriceService
	
    def updateAllPrices(){
		stockPriceService.updateAllPrices();
		redirect(action: "list", params: params)
	}
	
	def updatePrice( String id ){
		def stockInstance = Stock.get(id)
		if (!stockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
			redirect(action: "list")
			return
		}
		
		stockPriceService.updatePrices(stockInstance)
		
		flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockCode])
		redirect(action: "show", id: stockInstance.id)
		
	}
	
	def gatherStocks() {
		stockInfoService.gatherStocks()
		redirect(action: "list", params: params)
	}
	

    def show(String id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        [stockInstance: stockInstance]
    }

    def edit(String id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        [stockInstance: stockInstance]
    }

    def update(String id, Long version) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stockInstance.version > version) {
                stockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'stock.label', default: 'Stock')] as Object[],
                          "Another user has updated this Stock while you were editing")
                render(view: "edit", model: [stockInstance: stockInstance])
                return
            }
        }

        stockInstance.properties = params

        if (!stockInstance.save(flush: true)) {
            render(view: "edit", model: [stockInstance: stockInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stockInstance.stockCode])
        redirect(action: "show", id: stockInstance.id)
    }

    def delete(String id) {
        def stockInstance = Stock.get(id)
        if (!stockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
            return
        }

        try {
            stockInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), id])
            redirect(action: "show", id: id)
        }
    }
}
