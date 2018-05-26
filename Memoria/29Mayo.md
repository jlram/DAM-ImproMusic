Documento entregado para revisión el día 29/05/2018
## CORRECCIÓN DE ERRORES:

#### Al fin he solucionado el principal problema que tenía: los listViews. Ya puedo seguir trabajando en mi aplicación normalmente.

### Lista de tareas

- [X] Corregir error de los ListView 
- [ ] Crear lista de participaciones
- [X] Añadir enlace de youtube a la participación
- [X] Añadir enlace a la cancion y empezar a reproducirla
- [ ] Terminar la configuración del perfil
- [ ] Implementar función de reproducir desde la lista de canciones

# Cambios en el diseño

## MainActivity

- Cambios en la tipografía y el color de la fuente.
- Sustitución del imageView por un imageButton.
- Opción de poder reproducir cada canción desde el listView.
- Elemento EqualizerView para indicar qué cancion suena.
- Uso de un gradient para el fondo, en vez de un color sólido.
- Cambio de colores en el floating action button.


<img src="https://i.imgur.com/k6VXxUM.png" width="40%">


## SongActivity

- Ahora las canciones cuentan con el atributo "cover"
- Se muestra la imagen gracias a Picasso().
- imageButton a modo de toggle para reproducir en bucle.
- Cambio en el color y tamaño de la fuente.
- Estilo nuevo y más simple para los botones.
- Eliminación de texto en botones para poner logos.
- Uso del mismo gradient para el fondo.
- Vinculación del seekBar y el mediaPlayer para elegir en qué momento reproducir la canción.


<img src="https://i.imgur.com/KgrMD4r.png" width="40%">


# Avances de programación

ya redactaré esto xd

# Otros cambios

- Obtención de un dominio con base de datos para poder acceder a todas mis tablas sin la necesidad de trabajar en localhost.<a href="hyperbruh.webhostapp.com"></a>