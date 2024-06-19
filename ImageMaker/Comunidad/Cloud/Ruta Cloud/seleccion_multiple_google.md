

```javascript

function onEdit(e) {
  var sheet = e.source.getActiveSheet();
  var range = e.range;
  
  // Rango específico donde quieres habilitar la selección múltiple
  var dataValidationRange = "'Devops+ Cloud- César Ogalde'!D13:D48"; 
  
  if (range.getA1Notation() == dataValidationRange) {
    var oldValue = e.oldValue;
    var newValue = e.value;

    if (!oldValue) {
      // Primera vez que se selecciona un valor
      range.setValue(newValue);
    } else {
      // Ya hay un valor existente, agregar el nuevo valor
      var newValueArray = newValue.split(',').map(function(item) {
        return item.trim();
      });

      if (newValueArray.indexOf(oldValue) == -1) {
        newValue = oldValue + ', ' + newValue;
      }

      range.setValue(newValue);
    }
  }
}



```