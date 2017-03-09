$(function(){
    findBrands();

    var vm = new Vue({
        el: '#item-list',
        data: {
            items: [],
            pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 3},
            pageNums: [],
            searchConditions: {brandId: "", modelId:""},
            itemForm: {itemId: "", brand: "", model: "", itemName: "", unitPrice: ""
                        , discountPrice:"", itemDetailImage:"", itemListImage:"", stock:"", state: ""}
        },
        created: function () {
            this.query(1);
        },
        watch: {
            itemForm: function (newValue, oldValue) {
                validateForm(this.validateFields);
            }
        },
        methods: {
            query: function (page) {
                this.pagination.currentPage = page;

                this.searchConditions.brandId = $('#brandSearch').val();
                this.searchConditions.modelId = $('#modelSearch').val();

                var queryParas = {brandId: this.searchConditions.brandId, modelId: this.searchConditions.modelId,
                    currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType:"json",
                    url: "http://localhost:8080/admin/item-list",
                    data: queryParas,
                    success: function (res) {
                        if(res.code == "0"){
                            vm.items = res.data;
                            vm.pagination.listCount = res.count;
                            setPagination();

                            if(res.count == 0){
                                alert("Relevant information is not available！");
                            }
                        }
                    }
                });
            },
            search: function () {
                this.query(1);
            },
            queryCurrentPage: function(event){
                this.query(parseInt(event.target.text));
            },
            previousPage: function () {
                vm.pagination.currentPage -= 1;

                this.query(vm.pagination.currentPage);

            },
            nextPage: function () {
                vm.pagination.currentPage += 1;

                this.query(vm.pagination.currentPage);
            },
            addItem: function () {
                // alert(this.validateFields.brand.validators.notEmpty.message);
                clearFormElements(this.itemForm);
            },
            editItem: function (event) {


                for(var index in this.items){
                    if(event.target.id == this.items[index].itemId){
                        this.itemForm.itemId = this.items[index].itemId;
                        this.itemForm.brand = this.items[index].brandId;
                        this.itemForm.model = this.items[index].modelId;
                        this.itemForm.itemName = this.items[index].itemName;
                        this.itemForm.unitPrice = this.items[index].unitPrice;
                        this.itemForm.discountPrice = this.items[index].discountPrice;
                        this.itemForm.stock = this.items[index].stock;
                        this.itemForm.state = this.items[index].state;
                    }
                }

                $.ajax({
                    type: "GET",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "http://localhost:8080/api/models",
                    data: {brandId: this.itemForm.brand},
                    success: function (res) {
                        if(res.code == 0){
                            if(res["rs"].length > 1){
                                $('#model').html("");
                                for(var index in res["rs"]){
                                    if(vm.itemForm.model == res["rs"][index].value ){
                                        $('#model').append("<option selected='selected' value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                                    }else{
                                        $('#model').append("<option value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                                    }

                                }
                            }else{
                                $('#model').html("");
                            }
                        }else{
                            alert(res.message);
                        }
                    }
                });


            },
            saveItem: function () {
                if(validateForm(this.validateFields)){
                    return;
                }

                var formData = new FormData($('#itemForm')[0]);

                $.ajax({
                    type: "post",
                    dataType:"json",
                    url: "http://localhost:8080/admin/items",
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: formData,
                    success: function (res) {
                        if(res.code == "0"){
                            $('#itemFormModal').modal('hide');
                            vm.query(vm.pagination.currentPage);
                        }else{
                            alert(res.message);
                        }
                    }
                });
            }

        }
    });

    vm.validateFields = {brand:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The brand is required'
                                    }
                                }
                            }
                        , model:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The model is required'
                                    }
                                }
                            }
                        , itemName:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The item name is required'
                                    }
                                }
                            }
                        , unitPrice:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The unit price is required'
                                    }
                                }
                            }
                        , stock:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The stock is required'
                                    }
                                }
                            }
                        , state:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The state is required'
                                    }
                                }
                            }


                        };

    function validateForm(validateFields) {
        var ret = false;

        for(var fieldName in validateFields){

            var validators = validateFields[fieldName].validators;

            for(var validatorName in validators){
                if(validatorName == "notEmpty"){
                    if($('#' + fieldName).val() == null || $('#' + fieldName).val() == ""){
                        $('#'+fieldName + ' ~ .error-message').remove();
                        $('#'+fieldName).after("<span class='error-message'>"+ validators[validatorName].message +"</span>");
                        ret = true;
                    }else{
                        $('#'+fieldName + ' ~ .error-message').remove();
                    }
                }
            }


        }



        return ret;
    }

    function setPagination() {

        if(vm.pagination.listCount % vm.pagination.size == 0){
            vm.pagination.totalPage  = vm.pagination.listCount / vm.pagination.size;
        }else{
            vm.pagination.totalPage  = parseInt(vm.pagination.listCount / vm.pagination.size) + 1;
        }

        if(vm.pagination.currentPage > vm.pagination.endPage){
            vm.pagination.startPage = vm.pagination.currentPage;

            if(vm.pagination.totalPage - vm.pagination.currentPage + 1 >= vm.pagination.pageSize){
                vm.pagination.endPage = vm.pagination.currentPage + vm.pagination.pageSize -1;
            }else{
                vm.pagination.endPage = vm.pagination.totalPage;
            }

        }else if(vm.pagination.currentPage < vm.pagination.startPage){
            vm.pagination.endPage = vm.pagination.currentPage;
            vm.pagination.startPage = vm.pagination.endPage - vm.pagination.pageSize + 1;
        }else{
            if(vm.pagination.totalPage > vm.pagination.pageSize){
                vm.pagination.endPage = vm.pagination.pageSize;
            }else{
                vm.pagination.endPage = vm.pagination.totalPage;
            }
        }

        vm.pageNums = [];
        for(var i = 0; i < (vm.pagination.endPage - vm.pagination.startPage + 1); i++){
            vm.pageNums.push(i + vm.pagination.startPage);
        }
    }
    
    function clearFormElements(form) {
        for(var index in form){
            form[index] = "";
        }
    }


});

function findBrands() {
    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: "json",
        url: "http://localhost:8080/api/brands",
        success: function (res) {
            if(res.code == 0){
                for(var index in res["rs"]){
                    $('#brandSearch').append("<option value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                    $('#brand').append("<option value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                }


            }else{
                alert(res.message);
            }
        }
    });
}

function findModels(object) {

    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: "json",
        url: "http://localhost:8080/api/models",
        data: {brandId: object.value},
        success: function (res) {
            if(res.code == 0){
                if(res["rs"].length > 1){
                    $('#modelSearch').html("");
                    for(var index in res["rs"]){
                        $('#modelSearch').append("<option value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                    }
                }else{
                    $('#modelSearch').html("");
                }


            }else{
                alert(res.message);
            }
        }
    });
}


function findSearchModels(object) {

    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: "json",
        url: "http://localhost:8080/api/models",
        data: {brandId: object.value},
        success: function (res) {
            if(res.code == 0){
                if(res["rs"].length > 1){
                    $('#modelSearch').html("");
                    for(var index in res["rs"]){
                        $('#modelSearch').append("<option value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                    }
                }else{
                    $('#modelSearch').html("");
                }


            }else{
                alert(res.message);
            }
        }
    });
}

function findModels(object) {

    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: "json",
        url: "http://localhost:8080/api/models",
        data: {brandId: object.value},
        success: function (res) {
            if(res.code == 0){
                if(res["rs"].length > 1){
                    $('#model').html("");
                    for(var index in res["rs"]){
                        $('#model').append("<option value='" + res["rs"][index].value + "'>" + res["rs"][index].text + "</option>");
                    }
                }else{
                    $('#model').html("");
                }


            }else{
                alert(res.message);
            }
        }
    });
}


