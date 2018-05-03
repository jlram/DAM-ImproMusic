Documento entregado para revisión el día 03/05/2018
### DETECCION DE ERRORES:

###### A la hora de intentar entrar como invitado mediante el botón, no carga la lista de canciones. He creado una branch para seguir investigando, por ahora sin ningún fin satisfactorio.  
  
    
      
        
# Actividad de reproducción de música
##### (Diseño básico)
#### Actividad en la cual vamos a ver la canción seleccionada en la vista de canciones. El usuario podrá reproducir o parar la canción, ver los retos que están asociados a la misma o crear uno nuevo desde esta canción. 


# Actividad de Creación de retos
##### (Diseño básico)
#### En esta actividad podremos crear un reto a partir de unos campos de texto y spinners. Se podrá elegir tanto nombre, la canción, duración del reto y descripción básica. A la hora de crear reto, he introducido una función en usuarios.php: 

```      case "crearReto":
          $name=$_GET["name"];
          $id_song=$_GET["id_song"];
          $id_user=$_GET["id_user"];
          $creat_date=$_GET["creat_date"];
          $fin_date=$_GET["fin_date"];
          $descr=$_GET["descr"];
          $query="INSERT INTO Challenge values (null, '$name',
            $id_song,$id_user,null,'$creat_date',
            '$fin_date', '$descr');";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;
```
