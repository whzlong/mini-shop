$(function () {
   var vm = new Vue({
       el: "#coupon-list",
       data: {
           coupons: [],
           pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 3},
           pageNums: [],
           searchConditions: {code: "", couponName:"", state:""},
           pageForm: {code: "", couponName: "", validDateFrom: "", validDateTo: "", state: ""}
       },
       created: function () {
           this.query(1);
       },
       methods: {
           query: function (page) {
               this.pagination.currentPage = page;

               this.searchConditions.code = $('#codeSearch').val();
               this.searchConditions.couponName = $('#couponNameSearch').val();
               this.searchConditions.state = $('#stateSearch').val();

               var queryParas = {code: this.searchConditions.code
                                , state: this.searchConditions.state
                                , couponName: this.searchConditions.couponName
                                , currentPage: this.pagination.currentPage
                                , size: this.pagination.size};

               $.ajax({
                   type: "get",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   dataType:"json",
                   url: "http://localhost:8080/admin/coupon-list",
                   data: queryParas,
                   success: function (res) {
                       if(res.code == "0"){
                           vm.coupons = res.data;
                           vm.pagination.listCount = res.count;
                           setPagination();

                           if(res.count == 0){
                               alert("Relevant information is not available！");
                           }
                       }else{
                           alert(res.message);
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
               clearFormElements(this.pageForm);

               this.pageForm.state = "Unused";
           },
           editItem: function (event) {
               for(var index in this.coupons){
                   if(event.target.id == this.coupons[index].code){
                       this.pageForm.code = this.coupons[index].code;
                       this.pageForm.couponName = this.coupons[index].couponName;
                       this.pageForm.validDateFrom = this.coupons[index].validDateFrom;
                       this.pageForm.validDateTo = this.coupons[index].validDateTo;
                       this.pageForm.state = this.coupons[index].state;
                   }
               }
           },
           saveItem: function () {
               vm.pageForm.validDateFrom = $('#validDateFrom').val();
               vm.pageForm.validDateTo = $('#validDateTo').val();

               $.ajax({
                   type: "post",
                   dataType:"json",
                   url: "http://localhost:8080/admin/coupons",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   data: vm.pageForm,
                   success: function (res) {
                       if(res.code == "0"){
                           $('#pageFormModal').modal('hide');
                           vm.query(vm.pagination.currentPage);
                       }else{
                           alert(res.message);
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

    function clearFormElements(form) {
        for(var index in form){
            form[index] = "";
        }
    }

    $( "#validDateFrom" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1
    });

    $( "#validDateTo" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1
    });
});