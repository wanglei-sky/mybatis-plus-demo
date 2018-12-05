<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>paypal demo</title>
<script src="https://js.braintreegateway.com/web/dropin/1.11.0/js/dropin.min.js"></script>
<!-- paypal -->
<script src="https://www.paypalobjects.com/api/checkout.js" data-version-4 log-level="warn"></script>
<script src="https://js.braintreegateway.com/web/3.35.0/js/client.min.js"></script>
<script src="https://js.braintreegateway.com/web/3.35.0/js/paypal-checkout.min.js"></script>

<script src="http://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous"></script>
</head>
<body>
   <div id="dropin-wrapper">
	  <div id="checkout-message"></div>
	  <div id="dropin-container"></div>
	  <button id="submit-button">Submit payment</button>
	</div>
  <script>
  	var token = '${token}';
  	var button = document.querySelector('#submit-button');

    braintree.dropin.create({
      // Insert your tokenization key here
      authorization: token,
      container: '#dropin-container'
    }, function (createErr, instance) {
      button.addEventListener('click', function () {
        instance.requestPaymentMethod(function (requestPaymentMethodErr, payload) {
          // When the user clicks on the 'Submit payment' button this code will send the
          // encrypted payment information in a variable called a payment method nonce
          console.log(payload.nonce);
          $.ajax({
            type: 'POST',
            url: '/payment/checkout',
            data: {'paymentNonce': payload.nonce},
            dataType: 'json'
          }).done(function(result) {
            // Tear down the Drop-in UI
            instance.teardown(function (teardownErr) {
              if (teardownErr) {
                console.error('Could not tear down Drop-in UI!');
              } else {
                console.info('Drop-in UI has been torn down!');
                // Remove the 'Submit payment' button
                $('#submit-button').remove();
              }
            });

            if (result.success) {
              $('#checkout-message').html('<h1>Success</h1><p>Your Drop-in UI is working! Check your <a href="https://sandbox.braintreegateway.com/login">sandbox Control Panel</a> for your test transactions.</p><p>Refresh to try another transaction.</p>');
            } else {
              console.log(result);
              $('#checkout-message').html('<h1>Error</h1><p>Check your console.</p>');
            }
          });
        });
      });
    });
  </script>
  
  
  <div id="paypal-button" style="margin-top: 20px;"></div>
  <script>
  braintree.client.create({
	  authorization: token
	}, function (clientErr, clientInstance) {

	  // Stop if there was a problem creating the client.
	  // This could happen if there is a network error or if the authorization
	  // is invalid.
	  if (clientErr) {
	    console.error('Error creating client:', clientErr);
	    return;
	  }

	  // Create a PayPal Checkout component.
	  braintree.paypalCheckout.create({
	    client: clientInstance
	  }, function (paypalCheckoutErr, paypalCheckoutInstance) {

	    // Stop if there was a problem creating PayPal Checkout.
	    // This could happen if there was a network error or if it's incorrectly
	    // configured.
	    if (paypalCheckoutErr) {
	      console.error('Error creating PayPal Checkout:', paypalCheckoutErr);
	      return;
	    }

	    // Set up PayPal with the checkout.js library
	    paypal.Button.render({
	      env: 'sandbox', // or 'sandbox'
	      commit: true, // This will add the transaction amount to the PayPal button
	      payment: function () {
	        return paypalCheckoutInstance.createPayment({
	          // Your PayPal options here. For available options, see
	          // http://braintree.github.io/braintree-web/current/PayPalCheckout.html#createPayment
	           flow: 'checkout', // Required
	          amount: 10.00, // Required
	          currency: 'USD', // Required
	          enableShippingAddress: true,
	          shippingAddressEditable: false,
	          shippingAddressOverride: {
	            recipientName: 'Scruff McGruff',
	            line1: '1234 Main St.',
	            line2: 'Unit 1',
	            city: 'Chicago',
	            countryCode: 'US',
	            postalCode: '60652',
	            state: 'IL',
	            phone: '123.456.7890'
	          }
	          
	        });
	      },

	      onAuthorize: function (data, actions) {
	        return paypalCheckoutInstance.tokenizePayment(data, function (err, payload) {
	          // Submit `payload.nonce` to your server.
	          $.ajax({
	              type: 'POST',
	              url: '/payment/checkout',
	              data: {'paymentNonce': payload.nonce},
	              dataType: 'json'
	            }).done(function(result) {
	            	if (result.success) {
	                    alert('pay success');
	                  } else {
	                    console.log(result);
	                    alert(result.errorString);
	                  }
	            });
	        });
	      },

	      onCancel: function (data) {
	        console.log('checkout.js payment cancelled', JSON.stringify(data, 0, 2));
	      },

	      onError: function (err) {
	        console.error('checkout.js error', err);
	      }
	    }, '#paypal-button').then(function () {
	      // The PayPal button will be rendered in an html element with the id
	      // `paypal-button`. This function will be called when the PayPal button
	      // is set up and ready to be used.
	    });

	  });

	});
  </script>
</body>
</html>