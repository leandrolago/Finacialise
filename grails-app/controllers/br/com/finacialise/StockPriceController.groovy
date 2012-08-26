package br.com.finacialise

import org.springframework.dao.DataIntegrityViolationException;

class StockPriceController {
	
	def scaffold = true
	
	def show(String id) {
		def stockPriceInstance = StockPrice.get(id)
		if (!stockPriceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), id])
			redirect(action: "list")
			return
		}

		[stockPriceInstance: stockPriceInstance]
	}
	
	def edit(String id) {
		def stockPriceInstance = StockPrice.get(id)
		if (!stockPriceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), id])
			redirect(action: "list")
			return
		}

		[stockPriceInstance: stockPriceInstance]
	}
	
	def update(String id, Long version) {
		def stockPriceInstance = StockPrice.get(id)
		if (!stockPriceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (stockPriceInstance.version > version) {
				stockPriceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'stockPrice.label', default: 'StockPrice')] as Object[],
						  "Another user has updated this Stock while you were editing")
				render(view: "edit", model: [stockPriceInstance: stockPriceInstance])
				return
			}
		}

		stockPriceInstance.properties = params

		if (!stockPriceInstance.save(flush: true)) {
			render(view: "edit", model: [stockPriceInstance: stockPriceInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), stockPriceInstance.id])
		redirect(action: "show", id: stockPriceInstance.id)
	}
	
	def delete(String id) {
		def stockPriceInstance = StockPrice.get(id)
		if (!stockPriceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), id])
			redirect(action: "list")
			return
		}

		try {
			stockPriceInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'stockPrice.label', default: 'StockPrice'), id])
			redirect(action: "show", id: id)
		}
	}
   
}
