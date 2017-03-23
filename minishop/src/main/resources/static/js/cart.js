$(function () {
    $('#updateCartLabel').show();
    $('.sk-circle').hide();


    var vm = new Vue({
        el: '#cartForm',
        data: {
            cartId: "",
            cartItems: [],
            totalPrice: 0.00,
            currency: "",
            clientToken: "",
            checkedStockResult: [],
            hasPageError: false
        },
        created: function () {
            this.currency = $('#currency').val();

            $.ajax({
                type: "GET",
                dataType:"json",
                url: "/client/cart-items",
                success: function (res) {
                    if(res.code == "0"){
                        vm.cartId = res.rs["cartId"];
                        vm.cartItems = res.rs["cartItemVos"];
                        vm.totalPrice = res.rs["totalPrice"];
                        vm.currency = res.rs["currency"];

                        for(var index in vm.cartItems){
                            vm.cartItems[index].errorClass = "";
                            vm.cartItems[index].hasError = false;
                        }
                    }

                    if(vm.cartItems.length == 0){
                        $('#btnUpdateCart').attr("disabled", "true");
                        $('#checkout').attr("disabled", "true");
                    }
                }
            });

        },
        methods: {
            deleteCartItem: function (event) {
                if(confirm("Are you sure you want to delete this item?")){
                    $.ajax({
                        type: "POST",
                        dataType:"json",
                        url: "/client/cart-items/" + event.target.id + "/delete",
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
                        url: "/client/cart-items/update",
                        data: JSON.stringify(cartItemForms),
                        success: function(res){
                            if(res.code == "0"){
                                vm.cartItems = res.rs["cartItemVos"];
                                vm.totalPrice = res.rs["totalPrice"];
                            }else{
                                alert(res.message);
                            }


                            $('#updateCartLabel').show();
                            $('.sk-circle').hide();
                            $('#btnUpdateCart').removeAttr("disabled");
                        }
                    });
                }

            },
            checkout: function () {
                $.ajax({
                    type: "GET",
                    dataType:"json",
                    url: "/item-stock/cart",
                    data: {cartId: vm.cartId},
                    success: function (res) {
                        if(res.code == "0"){
                            if(!vm.checkStock(res.rs)){
                                window.location.href = "/client/check-out";
                            }
                        }

                    }
                });

            },
            checkStock: function (checkedStockResult) {
                var hasPageError = false;

                for(var index in vm.cartItems){
                    for(var stockIndex in checkedStockResult){
                        if(checkedStockResult[stockIndex].itemId == vm.cartItems[index].itemId
                            && checkedStockResult[stockIndex]['stockStatus'] == "0"){

                            vm.cartItems[index].errorClass = "has-error";
                            vm.cartItems[index].hasError = true;

                            Vue.set(vm.cartItems, index, vm.cartItems[index]);

                            hasPageError = true;

                            break;
                        }
                    }
                }

                return hasPageError;
            }
        }

    });




})