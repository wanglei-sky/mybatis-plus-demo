package com.busd.web.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private BraintreeGateway braintreeGateway;

	@RequestMapping("/demo")
	public String demo(HttpServletRequest request) {
		String token = braintreeGateway.clientToken().generate();
		request.setAttribute("token", token);
		return "demo";
	}

	@ResponseBody
	@RequestMapping("/token")
	public String payment() {
		return braintreeGateway.clientToken().generate();
	}

	@ResponseBody
	@RequestMapping(value = "/checkout", method = { RequestMethod.POST })
	public String postForm(@RequestParam(name="payAmount",defaultValue="10")  BigDecimal payAmount,  @RequestParam("paymentNonce") String nonce,
			Model model, final RedirectAttributes redirectAttributes) {
		TransactionRequest request = new TransactionRequest().amount(payAmount).paymentMethodNonce(nonce).options()
				.submitForSettlement(true).done();

		Result<Transaction> result = braintreeGateway.transaction().sale(request);
		JsonObject responseBody = new JsonObject();
		if (result.isSuccess()) {
			responseBody.addProperty("success", true);
		} else {
			String errorString = "";
			for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
				errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
			}
			responseBody.addProperty("success", false);
			responseBody.addProperty("errorString", errorString);
		}
		return responseBody.toString();
	}

}
