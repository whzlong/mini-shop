$(function () {
    var vm = new Vue({
        el: '#cartForm',
        data: {
            cartItems: [],
            totalPrice: 0.00,
            currency:""
        },
        created: function () {
            $.ajax({
                type: "GET",
                contentType: "application/json;charset=UTF-8",
                dataType:"json",
                url: "http://localhost:8080/client/cart-items",
                success: function (res) {
                    if(res.code == "0"){
                        vm.cartItems = res.rs["cartItemVos"];
                        vm.totalPrice = res.rs["totalPrice"];
                        vm.currency = res.rs["currency"];
                    }
                }
            });
        },
        methods: {
            deleteCartItem: function (event) {
                if(confirm("Are you sure you want to delete this item?")){
                    $.ajax({
                        type: "POST",
                        contentType: "application/json;charset=UTF-8",
                        dataType:"json",
                        url: "http://localhost:8080/client/cart-items/" + event.target.id + "/delete",
                        success: function(res){
                            if(res.code == "0"){
                                vm.cartItems = res.rs["cartItemVos"];
                                vm.totalPrice = res.rs["totalPrice"];
                            }
                        }

                    });


                }

            },
            updateCart: function (event) {

                if(this.cartItems.length > 0){
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
                        }
                    });
                }

            }
        }

    });
})