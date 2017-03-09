$(function () {
    $('#updateCartLabel').show();
    $('.sk-circle').hide();


    var vm = new Vue({
        el: '#cartForm',
        data: {
            cartItems: [],
            totalPrice: 0.00,
            currency: "",
            clientToken: ""
        },
        created: function () {
            this.currency = $('#currency').val();

            $.ajax({
                type: "GET",
                dataType:"json",
                url: "http://localhost:8080/client/cart-items",
                success: function (res) {
                    if(res.code == "0"){
                        vm.cartItems = res.rs["cartItemVos"];
                        vm.totalPrice = res.rs["totalPrice"];
                        vm.currency = res.rs["currency"];
                    }

                    if(vm.cartItems.length == 0){
                        $('#btnUpdateCart').attr("disabled", "true");
                        $('#checkout').attr("disabled", "true");
                    }
                }
            });

            // $.ajax({
            //     type: "GET",
            //     contentType: "application/json;charset=UTF-8",
            //     dataType:"json",
            //     url: "http://localhost:8080/client/cart-items",
            //     success: function (res) {
            //         if(res.code == "0"){
            //             vm.cartItems = res.rs["cartItemVos"];
            //             vm.totalPrice = res.rs["totalPrice"];
            //             vm.currency = res.rs["currency"];
            //         }
            //     }
            // });

        },
        methods: {
            deleteCartItem: function (event) {
                if(confirm("Are you sure you want to delete this item?")){
                    $.ajax({
                        type: "POST",
                        dataType:"json",
                        url: "http://localhost:8080/client/cart-items/" + event.target.id + "/delete",
                        success: function(res){
                            if(res.code == "0"){
                                vm.cartItems = res.rs["cartItemVos"];
                                vm.totalPrice = res.rs["totalPrice"];
                            }

                            if(vm.cartItems.length == 0){
                                $('#btnUpdateCart').attr("disabled", "true");
                                $('#checkout').attr("disabled", "true");
                            }
                        }

                    });


                }

            },
            updateCart: function (event) {

                if(this.cartItems.length > 0){
                    $('#updateCartLabel').hide();
                    $('#btnUpdateCart').attr("disabled", "true");

                    $('.sk-circle').show();




                    var cartItemForm = {};
                    var cartItemForms = new Array();

                    for(var i = 0; i < this.cartItems.length; i++){
                        cartItemForm = {};
                        cartItemForm.itemId = this.cartItems[i].itemId;
                        cartItemForm.quantity = this.cartItems[i].quantity;

                        cartItemForms.push(cartItemForm);
                    }

                    $.ajax({
                        type: "POST",
                        contentType: "application/json;charset=UTF-8",
                        dataType:"json",
                        url: "http://localhost:8080/client/cart-items/update",
                        data: JSON.stringify(cartItemForms),
                        success: function(res){
                            if(res.code == "0"){
                                vm.cartItems = res.rs["cartItemVos"];
                                vm.totalPrice = res.rs["totalPrice"];
                            }


                            $('#updateCartLabel').show();
                            $('.sk-circle').hide();
                            $('#btnUpdateCart').removeAttr("disabled");
                        }
                    });
                }

            }
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
                    amount: 10.00, // Required
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


})