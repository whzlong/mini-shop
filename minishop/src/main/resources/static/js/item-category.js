$(function () {
    var vm = new Vue({
        el: '#item-category',
        data: {
            brands: [],
            brandId: "",
            brandName: "",
            isActive: true,
            models: [],
            modelName: "",
            operateState: "add"
        },
        created: function () {

            $.ajax({
                type: "GET"
                , url: "http://localhost:8080/api/brands?blank=1"
                , success: function (result) {
                    if(result.code == "0"){
                        for(var i = 0; i < result["rs"].length; i++){
                            result["rs"][i].selected = false;
                        }

                        result["rs"][0].selected = true;

                        vm.brands = result["rs"];

                        $.ajax({
                            type: "GET",
                            url: "http://localhost:8080/api/models?blank=1&brandId=" + result["rs"][0].value ,
                            success: function (result) {
                                if(result.code == "0"){
                                    vm.models = result.rs;
                                }
                            }
                        });

                    }else{
                        alert(result.message);
                    }
                }

            });

        },
        methods: {
            saveBrand: function (event) {
                if(this.brandName == ""){
                    alert("The brand name is required");
                    return;
                }

                $('#brandModal').modal('hide');

                var post_url = "http://localhost:8080/api/brands/add";

                if(this.operateState == "update"){
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/api/brands/update",
                        data: $('#brandForm').serialize(),
                        success: function (result) {
                            if(result.code == "0"){
                                for(var i = 0; i < vm.brands.length; i++){
                                    if(vm.brandId == vm.brands[i].value){
                                        vm.brands[i].text = vm.brandName;
                                    }
                                };
                            }
                        }
                    });
                }else{
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/api/brands/add",
                        data: $('#brandForm').serialize(),
                        success: function (result) {
                            if(result.code == "0"){
                                vm.brands.splice(vm.brands.length, 0, result.rs);
                            }
                        }
                    });
                }



            },
            findModels: function (event) {

                for(var i = 0; i < this.brands.length; i++){
                    if(event.target.id == this.brands[i].value){
                        this.brands[i].selected = true;
                    }else{
                        this.brands[i].selected = false;
                    }
                }

                $.ajax({
                    type: "GET",
                    url: "http://localhost:8080/api/models?blank=1&brandId=" + event.target.id ,
                    success: function (result) {
                        if(result.code == "0"){
                            vm.models = result.rs;
                        }
                    }
                });


            },
            editBrand: function () {
                for(var i = 0; i < this.brands.length; i++){
                    if(this.brands[i].selected == true){
                        this.brandId = this.brands[i].value;
                        this.brandName = this.brands[i].text;

                        break;
                    }
                }

                this.operateState = "update";
            },
            addBrand: function () {
                this.brandId = "";
                this.brandName = "";
                this.operateState = "add";
            },
            deleteBrand: function () {
                if(confirm("Are you sure you want to remove this brand?")){
                    for(var i = 0; i < this.brands.length; i++){
                        if(this.brands[i].selected == true){
                            this.brandId = this.brands[i].value;

                            break;
                        }
                    }

                    $.ajax({
                        type: "GET",
                        url: "http://localhost:8080/api/brands/delete?brandId=" + vm.brandId ,
                        success: function (result) {
                            if(result.code == "0"){
                                for(var i = 0; i < vm.brands.length; i++){
                                    if(vm.brandId == vm.brands[i].value){
                                        vm.brands.splice(i, 1);
                                        break;
                                    }
                                }
                            }
                        }
                    });;
                }
            }
        }

    });
});