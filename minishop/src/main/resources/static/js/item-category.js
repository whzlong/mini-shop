$(function () {

    var vm = new Vue({
        el: '#item-category',
        data: {
            brands: [],
            selectedBrandId: "",
            selectedBrandName: "",
            isActive: true,
            isActiveModel:true,
            models: [],
            selectedModelId: "",
            selectedModelName: "",
            operateState: "add",
            operateModelState: "add",
            hasModel: true,
            hasBrand: true
        },
        created: function () {

            $.ajax({
                type: "GET"
                , url: "/api/brands?blank=1"
                , success: function (result) {
                    if(result.code == "0"){

                        vm.brands = result["rs"];

                        //默认选中数据
                        if(vm.brands.length > 0){
                            setSelectedBrand(vm.brands[0].value);
                        }

                    }else{
                        alert(result.message);
                    }

                    if(vm.models.length == 0){
                        vm.hasModel = false;
                    }
                }

            });

        },
        methods: {
            saveBrand: function (event) {
                if(this.selectedBrandName == ""){
                    alert("The brand name is required");
                    return;
                }



                if(this.operateState == "update"){
                    $.ajax({
                        type: "POST",
                        url: "/admin/item-categories/update",
                        data: {categoryId: vm.selectedBrandId, categoryName: vm.selectedBrandName},
                        success: function (result) {
                            if(result.code == "0"){
                                for(var i = 0; i < vm.brands.length; i++){
                                    if(vm.selectedBrandId == vm.brands[i].value){
                                        vm.brands[i].text = vm.selectedBrandName;
                                    }
                                };
                            }

                            $('#brandModal').modal('hide');
                        }
                    });
                }else{
                    $.ajax({
                        type: "POST",
                        url: "/admin/item-categories/add",
                        data: {categoryName: vm.selectedBrandName},
                        success: function (res) {
                            if(res.code == "0"){
                                vm.brands.splice(vm.brands.length, 0, res.rs);
                                setSelectedBrand(res["rs"].value);
                            }

                            $('#brandModal').modal('hide');
                        }
                    });
                }

            },
            findModels: function (event) {
                setSelectedBrand(event.target.id);

            },
            editBrand: function () {
                if(this.selectedBrandId == ""){
                    alert("Please select the brand.");
                    return;
                }

                for(var i = 0; i < this.brands.length; i++){
                    if(this.brands[i].selected == true){
                        this.selectedBrandId = this.brands[i].value;
                        this.selectedBrandName = this.brands[i].text;

                        break;
                    }
                }

                this.operateState = "update";

                $('#brandModal').modal('toggle');
            },
            addBrand: function () {
                this.selectedBrandId = "";
                this.selectedBrandName = "";
                this.operateState = "add";
            },
            deleteBrand: function () {
                if(this.selectedBrandId == ""){
                    alert("Please select the brand.");
                    return;
                }

                if(confirm("Are you sure you want to remove this brand?")){
                    for(var i = 0; i < this.brands.length; i++){
                        if(this.brands[i].selected == true){
                            this.selectedBrandId = this.brands[i].value;

                            break;
                        }
                    }

                    $.ajax({
                        type: "post",
                        url: "/admin/item-categories/delete",
                        data: {categoryId: this.selectedBrandId},
                        success: function (result) {
                            if(result.code == "0"){
                                for(var i = 0; i < vm.brands.length; i++){
                                    if(vm.selectedBrandId == vm.brands[i].value){
                                        vm.brands.splice(i, 1);

                                        if(i <= vm.brands.length - 1){
                                            setSelectedBrand(vm.brands[i].value);
                                        }else if(vm.brands.length > 0){
                                            setSelectedBrand(vm.brands[i-1].value);
                                        }else{
                                            vm.selectedBrandId = "";
                                        }

                                        break;
                                    }
                                }

                            }
                        }
                    });
                }
            },
            addModel: function () {
                if(this.selectedBrandId == ""){
                    alert("Please select the brand.");
                    return;
                }

                this.selectedModelId = "";
                this.selectedModelName = "";
                this.operateModelState = "add";

                $('#modelModal').modal('toggle');

            },
            editModel: function () {
                if(this.selectedModelId == ""){
                    alert("Please select the model.");
                    return;
                }

                for(var i = 0; i < this.models.length; i++){
                    if(this.models[i].selected == true){
                        this.selectedModelId = this.models[i].value;
                        this.selectedModelName = this.models[i].text;

                        break;
                    }
                }

                this.operateModelState = "update";
            },
            deleteModel: function () {
                if(this.selectedModelId == ""){
                    alert("Please select the model.");
                    return;
                }

                if(confirm("Are you sure you want to remove this model?")){
                    for(var i = 0; i < this.models.length; i++){
                        if(this.models[i].selected == true){
                            this.selectedModelId = this.models[i].value;

                            break;
                        }
                    }

                    $.ajax({
                        type: "post",
                        url: "/admin/item-categories/delete",
                        data: {categoryId: this.selectedModelId},
                        success: function (result) {
                            if(result.code == "0"){
                                for(var i = 0; i < vm.models.length; i++){
                                    if(vm.selectedModelId == vm.models[i].value){
                                        vm.models.splice(i, 1);

                                        if(i <= vm.models.length - 1){
                                            setSelectedModel(vm.models[i].value);
                                        }else if(vm.models.length > 0){
                                            setSelectedModel(vm.models[i-1].value);
                                        }else{
                                            vm.selectedModelId = "";
                                        }

                                        break;
                                    }
                                }

                                if(vm.models.length == 0){
                                    vm.hasModel = false;
                                }

                            }
                        }
                    });
                }
            },
            saveModel: function () {
                if(this.selectedModelName == ""){
                    alert("The model name is required");
                    return;
                }



                if(this.operateModelState == "update"){
                    $.ajax({
                        type: "POST",
                        url: "/admin/item-categories/update",
                        data: {categoryId: this.selectedModelId, categoryName: this.selectedModelName},
                        success: function (result) {
                            if(result.code == "0"){
                                for(var i = 0; i < vm.models.length; i++){
                                    if(vm.selectedModelId == vm.models[i].value){
                                        vm.models[i].text = vm.selectedModelName;
                                    }
                                };
                            }

                            $('#modelModal').modal('hide');
                        }
                    });
                }else{

                    $.ajax({
                        type: "POST",
                        url: "/admin/item-categories/add",
                        data: {parentCategoryId: this.selectedBrandId, categoryName: this.selectedModelName},
                        success: function (res) {
                            if(res.code == "0"){
                                vm.models.splice(vm.models.length, 0, res.rs);
                                setSelectedModel(res["rs"].value);
                            }

                            $('#modelModal').modal('hide');
                        }
                    });
                }
            },
            selectModel: function (event) {
                setSelectedModel(event.target.id);
            }

        }

    });


    /**
     * 设置选中行（品牌）
     *
     * @param brandValue
     */
    function setSelectedBrand(brandValue) {
        for(var index in vm.brands){
            var newItem = {value: vm.brands[index].value, text: vm.brands[index].text, selected: true};

            if(brandValue == vm.brands[index].value){
                Vue.set(vm.brands, index, newItem);
                vm.selectedBrandId = brandValue;
            }else{
                newItem.selected = false;
                Vue.set(vm.brands, index, newItem);
            }
        }

        if(vm.brands.length > 0){
            vm.hasBrand = true;
        }else{
            vm.hasBrand = false;
        }

        getModelsByBrandId(brandValue);
    }

    /**
     * 设置选中行（型号）
     *
     * @param modelValue
     */
    function setSelectedModel(modelValue) {
        for(var index in vm.models){
            var newItem = {value: vm.models[index].value, text: vm.models[index].text, selected: true};

            if(modelValue == vm.models[index].value){
                Vue.set(vm.models, index, newItem);
                vm.selectedModelId = vm.models[index].value;
            }else{
                newItem.selected = false;
                Vue.set(vm.models, index, newItem);
            }
        }

        if(vm.models.length > 0){
            vm.hasModel = true;
        }else{
            vm.hasModel = false;
        }
    }
    
    function getModelsByBrandId(brandId) {
        $.ajax({
            type: "GET",
            url: "/api/models?blank=1&brandId=" + brandId ,
            success: function (result) {
                if(result.code == "0"){
                    vm.models = result.rs;
                    //默认选中行
                    if(vm.models.length > 0){
                        setSelectedModel(vm.models[0].value);
                        vm.hasModel = true;
                    }else{
                        vm.selectedModelId = "";
                        vm.selectedModelName = "";

                        vm.hasModel = false;
                    }

                }

            }
        });
    }
    
});