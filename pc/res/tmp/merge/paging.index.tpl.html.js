define(function(){return '<ul class="mui-pagination-ul"><li class=\'total-page\'>总页数<span>${totalPage}</span>页</li><li class="j_firstPage"><a href="#">首页</a></li><li class="j_prvPage"><a href="#">«</a></li>{@each itemArray as item,index}<li {@if item == currentIndex}class="active j_changePage"{@else}class="j_changePage"{@/if}><a href="#">${item}</a></li>{@/each}<li class="j_nextPage"><a href="#">»</a></li><li class=\'last j_lastPage\'><a href="#">尾页</a></li></ul>'});