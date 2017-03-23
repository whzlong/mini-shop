$(function () {
    var vm = new Vue({
        el: '#index-page',
        data: {
            newItems: [],
            discountItems: [],
            hasDiscountItems: false,
            hasNewItems: false
        },
        created: function () {
            $.ajax({
                type: "GET",
                dataType:"json",
                url: "anonym/special-items",
                success: function (res) {
                    if(res.code == "0"){
                        vm.newItems = res.rs["newItems"];
                        vm.discountItems = res.rs["discountItems"];

                        if(vm.discountItems.length > 0){
                            vm.hasDiscountItems = true;
                        }

                        if(vm.newItems.length > 0){
                            vm.hasNewItems = true;
                        }

                    }

                }
            });
        },
        methods: {
            selectService: function () {
                store.remove('selectedBrand');

                store.remove('selectedModel');

                window.location.href = "/client/select-service";
            },
            jumpToDetail: function (event) {
                window.location.href = "/client/item-detail/" + event.target.id;
            }
        }
    });
});