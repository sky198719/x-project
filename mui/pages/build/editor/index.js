define(["base","jHtmlArea"],function(t){return{init:function(e){var n=e.$dom;n.htmlarea(),n.htmlarea({toolbar:[["bold","italic","underline","|","forecolor"],["p","h1","h2","h3","h4","h5","h6"],["link","unlink","|","image"],[{css:"custom_disk_button",text:"Save",action:function(t){alert("SAVE!\n\n"+this.toHtmlString())}}]],toolbarText:t.extend({},jHtmlArea.defaultOptions.toolbarText,{bold:"fett",italic:"kursiv",underline:"unterstreichen"}),loaded:function(){t.log("htmlArea loaded!")}})}}});