<?php //configura los datos de tu cuenta
$dbhost='localhost';
$dbuser='root';
$dbpass='';
$dbname='api';

$conexion=mysqli_connect($dbhost,$dbuser,$dbpass) or die("<h3>***Error al conectar</h3>". mysqli_error($conexion));

$query="DROP DATABASE IF EXISTS $dbname";
mysqli_query($conexion,$query) or die("<h3>Error al borrar la base de datos</h3>". mysqli_error($conexion));


$query="CREATE DATABASE IF NOT EXISTS $dbname";
mysqli_query($conexion,$query) or die("<h3>Error al crear la base de datos</h3>". mysqli_error($conexion));
 
$query="
   CREATE TABLE IF NOT EXISTS Usuarios(
        id INTEGER(4) AUTO_INCREMENT,
        user VARCHAR(40) UNIQUE,
        pass VARCHAR(40),
        tipo VARCHAR(10),
        PRIMARY KEY(id));
   ";
  
 mysqli_select_db($conexion,$dbname) or die("<h3>Error al selecionar la BD</h3>".mysqli_error($conexion));

 mysqli_query($conexion,$query) or die("<h3>Error al crear la tabla</h3>". mysqli_error($conexion));
 
 echo "Tabla creada correctamente<br>-----------------------------------------<br>";
 $query= "INSERT INTO Usuarios VALUES 
                     (7000, 'administrador','123','admin'),
                     (7001, 'Alberto',  '456','normal'),
                     (7002, 'Maria',   '789','normal');";
 $result=mysqli_query($conexion,$query) or die("<h3>Error al insertar los datos</h3>". mysqli_error($conexion));
 echo "Datos insertados correctamente<br>--------------------------------------<br>";

 ?>