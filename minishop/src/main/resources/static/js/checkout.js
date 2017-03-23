$(function () {
    var vm = new Vue({
        el: '#checkout-page',
        data: {
            submitForm: {shippingAddress: "", shippingCity: "", shippingPostcode: "", payMethod:"", couponCode:""},
            payMethod: "0",
            selectedCoupons:[],
            payableAmount: 0,
            pageVo: {},
            coupons: [],
            validation: {address: {errorClass: "", notEmpty: false}, city: {errorClass: "", notEmpty: false}, postcode: {errorClass: "", notEmpty: false}}
        },
        created: function () {
            $.ajax({
                type: "GET",
                dataType:"json",
                url: "/client/check-out/init",
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
                            couponTotalAmount += vm.coupons[j].amount;
                            break;
                        }
                    }
                }

                vm.payableAmount = vm.pageVo.subtotal - couponTotalAmount;

            }
        },
        methods: {
            pay: function () {
                var hasError = false;

                if(vm.submitForm.shippingAddress == ""){
                    vm.validation.address.errorClass = "has-error";
                    vm.validation.address.notEmpty = true;

                    hasError = true;
                }else{
                    vm.validation.address.errorClass = "";
                    vm.validation.address.notEmpty = false;
                }

                if(vm.submitForm.shippingCity == ""){
                    vm.validation.city.errorClass = "has-error";
                    vm.validation.city.notEmpty = true;

                    hasError = true;
                }else{
                    vm.validation.city.errorClass = "";
                    vm.validation.city.notEmpty = false;
                }

                if(vm.submitForm.shippingPostcode == ""){
                    vm.validation.postcode.errorClass = "has-error";
                    vm.validation.postcode.notEmpty = true;

                    hasError = true;
                }else{
                    vm.validation.postcode.errorClass = "";
                    vm.validation.postcode.notEmpty = false;
                }

                if(!hasError){
                    $('#checkout').click();
                }

            }
        }

    });


    // var checkoutByPaypal = document.getElementById('checkout');

    // Create a Client component
    braintree.client.create({
        authorization: $('#clientToken').val()
    }, function (clientErr, clientInstance) {

        // if(clientErr){
        //     return;
        // }

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
                    // alert(tokenizationPayload.nonce);
                    $.ajax({
                        type: "POST",
                        dataType:"json",
                        url: "http://localhost:8080/client/checkouts",
                        data: {amount: vm.payableAmount, nonce: tokenizationPayload.nonce},
                        success: function (res) {
                            if(res.code == "0"){
                                window.location.href = "/client/checkouts/" + res.rs.id;
                            }else{
                                alert(res.message);
                            }
                        }
                    });

                });
            });

        });



        // if(vm.payMethod == "0"){
        //
        //
        //
        // }else{
        //
        //     braintree.hostedFields.create({
        //         client: clientInstance,
        //         styles: {
        //             'input': {
        //                 'font-size': '14pt'
        //             },
        //             'input.invalid': {
        //                 'color': 'red'
        //             },
        //             'input.valid': {
        //                 'color': 'green'
        //             }
        //         },
        //         fields: {
        //             number: {
        //                 selector: '#card-number',
        //                 placeholder: '4111 1111 1111 1111'
        //             },
        //             cvv: {
        //                 selector: '#cvv',
        //                 placeholder: '123'
        //             },
        //             expirationDate: {
        //                 selector: '#expiration-date',
        //                 placeholder: '10/2019'
        //             }
        //         }
        //     }, function (hostedFieldsErr, hostedFieldsInstance) {
        //         if (hostedFieldsErr) {
        //             // Handle error in Hosted Fields creation
        //             return;
        //         }
        //
        //         submit.removeAttribute('disabled');
        //     });
        //
        //
        // }



    });




});