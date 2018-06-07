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
          	$query="SELECT * FROM musician";
          	$resultado=mysqli_query($conexion,$query) or die("ERROR");
          	$json=TABLA_A_JSON($resultado);
          	echo $json;
    	break;

    	case "consultaPorID":
    		$id=$_GET["id"];
          	$query="SELECT * FROM musician WHERE ID = '$id'";
          	$resultado=mysqli_query($conexion,$query) or die("ERROR");
          	$json=TABLA_A_JSON($resultado);
          	echo $json;
    	break;

      case "login":
          $user=$_GET["username"];
          $pass=$_GET["password"];
          $query="SELECT * FROM musician where username ='$user' and password = '$pass' ";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

      case "insertar":
           $user=$_GET["username"];
           $pass=$_GET["password"];
           $logdate=$_GET["logdate"];
           $query="INSERT INTO musician(ID,username,password,log_date,user_type, id_pic) values (null,'$user','$pass','$logdate','normal','1');";
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
           $query="UPDATE musician SET password='$pass' WHERE username='$user';";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;

      /*CONSULTAS DE LA TABLA CANCIONES*/
      case "consultaCanciones":
          $query="SELECT * FROM song";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

      case "consultaCancionPorID":
      	  $song=$_GET["song"];
          $query="SELECT * FROM song WHERE ID = '$song'";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

       case "meterCancion":
           $name=$_GET["name"];
           $author=$_GET["author"];
           $album=$_GET["album"];
           $genre=$_GET["genre"];
           $cover=$_GET["cover"];
           $link=$_GET["link"];

           $query="INSERT INTO `song` (`ID`, `name`, `author`, `album`, `genre`, `cover`, `link`) VALUES (NULL, '$name', '$author', '$album', '$genre', '$cover', '$link');";
           $resultado=mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;

      case "eliminarCancion":
          $id_song=$_GET["id_song"];
          $query="DELETE FROM song WHERE ID = '$id_song'";
          mysqli_query($conexion,$query) or die("ERROR");
           echo '[{"estado":true}]';
      break;


      /*CONSULTAS DE LA TABLA RETOS*/
      case "crearReto":
          $name=$_GET["name"];
          $id_song=$_GET["id_song"];
          $id_user=$_GET["id_user"];
          $creat_date=$_GET["creat_date"];
          $fin_date=$_GET["fin_date"];
          $descr=$_GET["descr"];
          $query="INSERT INTO challenge values (null, '$name',$id_song,$id_user, null,'$creat_date','$fin_date', '$descr');";
           mysqli_query($conexion,$query) or die("ERROR");
           echo '{"estado":true}';
      break;

      case "consultaRetos":
          $query="SELECT * FROM challenge";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

      case "consultaRetosPorID":
        $id_song=$_GET["id_song"];
        $query="SELECT * FROM challenge WHERE ID_song = '$id_song'";
        $resultado=mysqli_query($conexion,$query) or die("ERROR");
        $json=TABLA_A_JSON($resultado);
        echo $json;
      break;

      /*CONSULTA DE LA TABLA PARTICIPACIONES*/

      case "consultaPart":
          $reto=$_GET["reto"];
          $query="SELECT * FROM participation WHERE ID_Chall = '$reto' ORDER BY `participation`.`votes` DESC ";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;

      /*CONSULTAS DE LAS TABLAS PARTICIPATION Y VOTO*/

      case "registrarParticipacion":
          $chall=$_GET["chall"];
          $music=$_GET["music"];
          $date=$_GET["date"];
          $youtube=$_GET["youtube"];
          $query="INSERT INTO `participation` (`ID_musician`, `ID_chall`, `part_date`, `votes`, `youtube`) VALUES ('$music', '$chall', '$date', '0', '$youtube');";
          mysqli_query($conexion,$query) or die("ERROR");
           echo '[{"estado":true}]';
      break;

      case "sumarVoto":
          // $chall=$_GET["chall"];
          // $music=$_GET["music"];
          $part=$_GET["part"];
          $query="UPDATE participation SET votes = votes + 1 where ID = '$part'";
          mysqli_query($conexion,$query) or die("ERROR");
           echo '[{"estado":true}]';
      break;

      case "registrarVoto":
          $chall=$_GET["chall"];
          $music=$_GET["music"];
          $part=$_GET["part"];
          $query="INSERT INTO vote values ('$part', '$music', '$chall');";
          mysqli_query($conexion,$query) or die("ERROR");
           echo '[{"estado":true}]';
      break;

      case "restarVoto":
          // $chall=$_GET["chall"];
          // $music=$_GET["music"];
          $part=$_GET["part"];
          $query="UPDATE participation SET votes = votes - 1 where ID= '$part'";
          mysqli_query($conexion,$query) or die("ERROR");
           echo '[{"estado":true}]';
      break;

      case "eliminarVoto":
          $chall=$_GET["chall"];
          $music=$_GET["music"];
          $part=$_GET["part"];
          $query="DELETE FROM vote WHERE ID_musician = '$music' AND ID_chall = '$chall' AND ID_part = '$part'";
          mysqli_query($conexion,$query) or die("ERROR");
           echo '[{"estado":true}]';
      break;

      case "consultarVoto":
          $chall=$_GET["chall"];
          $music=$_GET["music"];
          $part=$_GET["part"];
          $query="SELECT * FROM vote WHERE ID_musician = '$music' AND ID_chall = '$chall' AND ID_part= '$part'";
          $resultado=mysqli_query($conexion,$query) or die("ERROR");
          $json=TABLA_A_JSON($resultado);
          echo $json;
      break;
    }

?>
