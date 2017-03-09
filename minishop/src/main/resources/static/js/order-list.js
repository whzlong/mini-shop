$(function () {
    var vm = new Vue({
        el: '#order-list',
        data: {
            orders: [],
            pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, listCount: 0, pageSize: 3, size: 3},
            pageNums: [],
            searchConditions: {orderId: "", orderDateFrom:"", orderDateTo: "", state: "Appointment"},
            orderForm: {orderId: "", payDate: "", state: "", shipAddress: "", comment: ""},
            orderDetails: []
        },
        created: function () {
            this.query(1);
        },
        methods: {
            query: function (page) {
                this.pagination.currentPage = page;

                this.searchConditions.orderDateFrom = $('#orderDateFrom').val();
                this.searchConditions.orderDateTo = $('#orderDateTo').val();

                var queryParas = {orderId: this.searchConditions.orderId, orderDateFrom: this.searchConditions.orderDateFrom,
                    orderDateTo: this.searchConditions.orderDateTo, state: this.searchConditions.state,
                    currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType:"json",
                    url: "http://localhost:8080/admin/order-list",
                    data: queryParas,
                    success: function (res) {
                        if(res.code == "0"){
                            vm.orders = res.data;
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
            editItem: function (event) {
                clearFormElements(this.orderForm);

                $('#payDate').val("");

                for (var index in this.orders){

                    if(this.orders[index].orderId == event.target.id){
                        this.orderForm.orderId = this.orders[index].orderId;
                        this.orderForm.payDate = this.orders[index].payDate;
                        this.orderForm.shipAddress = this.orders[index].shipAddress;
                        this.orderForm.state = this.orders[index].state;
                        this.orderForm.comment = this.orders[index].comment;

                        break;
                    }
                }

            },
            updateItem: function (event) {

                this.orderForm.payDate = $('#payDate').val();

                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "http://localhost:8080/admin/orders/update",
                    data: this.orderForm,
                    success: function (res) {
                        if(res.code == 0){
                            $('#editOrderModal').modal('hide');
                            vm.query(vm.pagination.currentPage);

                            clearEditContent();
                        }
                    }
                });
            },
            deleteItem: function (event) {

                if(confirm("Are you sure you want to delete this item?")){
                    var paras = {orderId : event.target.id};

                    $.ajax({
                        type: "post",
                        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                        dataType: "json",
                        url: "http://localhost:8080/admin/orders/delete",
                        data: paras,
                        success: function (res) {
                            if(res.code == 0){
                                vm.query(vm.pagination.currentPage);
                            }
                        }
                    });
                }
            },
            showDetail: function () {
                var paras = {orderId : event.target.id};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "http://localhost:8080/admin/orderDetails",
                    data: paras,
                    success: function (res) {
                        if(res.code == 0){
                            vm.orderDetails = res.rs;
                        }
                    }
                });
            }
        }
    });


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

    function clearEditContent() {
        this.orderForm.orderId = "";
        this.orderForm.payDate = "";
        this.orderForm.shipAddress = "";
        this.orderForm.state = "";
        this.orderForm.comment = "";
    }

    function clearFormElements(form) {
        for(var index in form){
            form[index] = "";
        }
    }

    $( "#orderDateFrom" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1,
        clearBtn:true
    });

    $( "#orderDateTo" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1,
        clearBtn:true
    });

    $( "#payDate" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1,
        clearBtn:true
    });

});


