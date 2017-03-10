$(function () {
    var vm = new Vue({
        el: '#checkout-page',
        data: {
            submitForm: {shippingAddress: "", shippingCity: "", shippingPostcode: "", payMethod:"", couponCode:""},
            payMethod: "0",
            selectedCoupons:[],
            payableAmount: 0,
            pageVo: {},
            coupons: []
        },
        created: function () {
            $.ajax({
                type: "GET",
                dataType:"json",
                url: "http://localhost:8080/client/check-out",
                success: function (res) {
                    if(res.code == "0"){
                        vm.pageVo = res.rs;
                        vm.coupons = vm.pageVo.coupons;
                        vm.payableAmount = vm.pageVo.subtotal;
                    }else{
                        alert(res.message);
                    }
                }
            });
        },
        watch: {
            selectedCoupons: function (newCouponCodes) {
                var couponTotalAmount = 0;

                for(var i = 0; i < newCouponCodes.length; i++){
                    for(var j = 0; j < vm.coupons.length; j++){
                        if(newCouponCodes[i] == vm.coupons[j].couponCode){
                            couponTotalAmount += vm.coupons[i].amount;
                            break;
                        }
                    }
                }

                vm.payableAmount = vm.pageVo.subtotal - couponTotalAmount;

            }
        },
        methods: {

        }

    });


    // var checkoutByPaypal = document.getElementById('checkout');

    // Create a Client component
    braintree.client.create({
        authorization: $('#clientToken').val()
    }, function (clientErr, clientInstance) {
        // Create PayPal component
        braintree.paypal.create({
            client: clientInstance
        }, function (err, paypalInstance) {

            $('#checkout').click(function () {
                // Tokenize here!
                paypalInstance.tokenize({
                    flow: 'checkout', // Required
                    amount: vm.pageVo.subtotal, // Required
                    currency: 'GBP', // Required
                    locale: 'en_GB',
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
                }, function (err, tokenizationPayload) {
                    // Tokenization complete
                    // Send tokenizationPayload.nonce to server
                    alert(tokenizationPayload.nonce);


                });
            });

        });
    });




});