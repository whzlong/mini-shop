$(function(){
    var vm = new Vue({
        el: '#select-service',
        data: {
            brands: [],
            models: [],
            items: []
        },
        created: function () {
            this.findBrands();
        },
        methods: {
            findBrands: function () {
                var selectedBrand = store.get("selectedBrand");

                $.ajax({
                    type: "GET",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/api/brands",
                    data: {blank: "1"},
                    success: function (res) {
                        if(res.code == 0){
                            vm.brands = res.rs;

                            //查询型号
                            if(vm.brands.length > 0){
                                //设置选中项
                                if(typeof(selectedBrand) == "undefined"){
                                    selectedBrand = res['rs'][0].value
                                }else{
                                    store.remove("selectedBrand");
                                }

                                setSelectedBrand(selectedBrand);

                                vm.queryModels(selectedBrand);
                            }
                        }else{
                            alert(res.message);
                        }
                    }
                });
            },
            findModels: function (event) {
                setSelectedBrand(event.target.id);

                this.queryModels(event.target.id);
            },
            queryModels: function (brandId) {
                var selectedModel = store.get("selectedModel");

                $.ajax({
                    type: "GET",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/api/models",
                    data: {brandId: brandId, blank: "1"},
                    success: function (res) {
                        if(res.code == 0){
                            vm.models = res.rs;

                            if(vm.models.length > 0){
                                if(typeof(selectedModel) == "undefined"){
                                    selectedModel = vm.models[0].value
                                }else{
                                    store.remove("selectedModel");
                                }

                                setSelectedModel(selectedModel);

                                vm.queryItems(selectedModel);
                            }else{
                                vm.items = [];
                            }
                        }else{
                            alert(res.message);
                        }
                    }
                });
            },
            findItems: function (event) {
                setSelectedModel(event.target.id);

                this.queryItems(event.target.id);
            },
            queryItems: function (modelId) {
                $.ajax({
                    type: "GET",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/client/items/",
                    data: {modelId: modelId},
                    success: function (res) {
                        if(res.code == 0){
                            vm.items = res.rs;
                        }else{
                            alert(res.message);
                        }
                    }
                });
            },
            jumpToItemDetail: function (event) {
                window.location.href = "/client/item-detail/" + event.currentTarget.id;
            }
        }
    });


    /**
     * 选定品牌
     *
     * @param brandId
     */
    function setSelectedBrand(brandId) {
        for(var index in vm.brands){
            if(vm.brands[index].value == brandId){
                vm.brands[index].selected = true;
            }else{
                vm.brands[index].selected = false;
            }
        }
    }

    /**
     * 选择型号
     *
     * @param modelId
     */
    function setSelectedModel(modelId) {
        for(var index in vm.models){
            if(vm.models[index].value == modelId){
                vm.models[index].selected = true;
            }else{
                vm.models[index].selected = false;
            }
        }
    }

});