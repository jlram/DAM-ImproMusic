<?php
header('Content-Type: application/json; charset=utf-8');

function Conectar($host,$user,$pass,$dbname)
{
   $conexion=mysqli_connect($host,$user,$pass) or die("ERROR CONEXION");
   mysqli_select_db($conexion,$dbname) or die("ERROR CONEXION");
   
   return $conexion;
}

function TABLA_A_JSON($resultado)
{
   $respuesta=array();
   while($fila=mysqli_fetch_assoc($resultado))
   {
         array_push($respuesta, $fila);
   }
   return json_encode($respuesta);
}

$host='localhost';
$user='root';
$pass='';
$dbname='impromusic';

$conexion=Conectar($host,$user,$pass,$dbname);

   $opcion=$_GET["accion"];
   switch($opcion)
   {
      /*CONSULTAS DE LA BASE DE DATOS DE USUARIOS*/
      case "consulta":
          $query="SELECT * FROM Musician";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

      case "login":
          $user=$_GET["username"];
          $pass=$_GET["password"];
          $query="SELECT * FROM Musician where username ='$user' and password = '$pass' ";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);

          echo $json;
      break;

      case "insertar":
           $user=$_GET["username"];
           $pass=$_GET["password"];
           $logdate=$_GET["logdate"];
           $query="INSERT INTO Musician(ID,username,password,log_date,user_type, id_pic) values (null,'$user','$pass','$logdate','normal','1');";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;

      case "borrar":
           $user=$_GET["user"];
           $query="DELETE FROM usuarios WHERE user='$user';";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;
      
      case "actualizar":
           $user=$_GET["username"];
           $pass=$_GET["password"];
           $query="UPDATE Musician SET password='$pass' WHERE username='$user';";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;



      /*CONSULTAS DE LA TABLA CANCIONES*/
      case "consultaCanciones":
          $query="SELECT * FROM Song";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;


      /*CONSULTAS DE LA TABLA RETOS*/
      case "crearReto":
          $name=$_GET["name"];
          $id_song=$_GET["id_song"];
          $id_user=$_GET["id_user"];
          $creat_date=$_GET["creat_date"];
          $fin_date=$_GET["fin_date"];
          $descr=$_GET["descr"];
          $query="INSERT INTO Challenge values (null, '$name',$id_song,$id_user, null,'$creat_date','$fin_date', '$descr');";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;

      case "consultaRetos":
          $query="SELECT * FROM Challenge";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

      /*CONSULTA DE LA TABLA PARTICIPACIONES*/

      case "consultaRetos":
          $query="SELECT * FROM Participaciones";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;


    }

?>

