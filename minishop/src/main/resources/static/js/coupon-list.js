$(function () {
   var vm = new Vue({
       el: "#coupon-list",
       data: {
           coupons: [],
           pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 3},
           pageNums: [],
           searchConditions: {code: "", state:""},
           pageForm: {validDateFrom: "", validDateTo: "", state: ""}
       },
       created: function () {
           this.query(1);
       },
       methods: {
           query: function (page) {
               this.pagination.currentPage = page;

               this.searchConditions.code = $('#codeSearch').val();
               this.searchConditions.state = $('#stateSearch').val();

               var queryParas = {code: this.searchConditions.code, state: this.searchConditions.state,
                                currentPage: this.pagination.currentPage, size: this.pagination.size};

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
           }
       }
   });
});