/**
 * Created by gaoying on 15/10/27.
 */
weixinapp.filter('nl2br', function() {
       var span = document.createElement('span');
       return function(input) {
           if (!input) return input;
           var lines = input.split('\n');

           for (var i = 0; i < lines.length; i++) {
               span.innerText = lines[i];
               span.textContent = lines[i];  //for Firefox
               lines[i] = span.innerHTML;
           }
           return lines.join('<br />');
       }
   });