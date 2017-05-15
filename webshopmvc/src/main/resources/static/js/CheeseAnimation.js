var list;

function onclickAnimation(){
    
  list = document.querySelectorAll('#CheeseAnimation');

    for (var i = 0; i<list.length; i++){
        list[i].onclick=move;
       
    }
}

function move(){
   
   var image = getElementById('#CheeseAnimation');
   
   
}


window.onload=onclickAnimation;