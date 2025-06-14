-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: Restaurante
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bebidas`
--

DROP TABLE IF EXISTS `bebidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bebidas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_bebida` varchar(100) NOT NULL,
  `descripcion` text NOT NULL,
  `precio_mxn` decimal(8,2) NOT NULL,
  `tamaño` varchar(50) NOT NULL,
  `calorias` int DEFAULT NULL,
  `contenido_alcoholico` tinyint(1) DEFAULT '0',
  `producto_artesanal` tinyint(1) DEFAULT '0',
  `disponible_en_menu` tinyint(1) DEFAULT '1',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bebidas`
--

LOCK TABLES `bebidas` WRITE;
/*!40000 ALTER TABLE `bebidas` DISABLE KEYS */;
INSERT INTO `bebidas` VALUES (1,'Agua Natural','Agua purificada natural',25.00,'500ml',0,0,0,1,'2025-06-07 00:56:30','2025-06-07 00:56:30'),(2,'Coca Cola','Refresco de cola clásico',35.00,'355ml',140,0,0,1,'2025-06-07 00:56:30','2025-06-07 00:56:30'),(3,'Jugo de Naranja Natural','Jugo de naranja recién exprimido',45.00,'300ml',110,0,1,1,'2025-06-07 00:56:30','2025-06-07 00:56:30'),(4,'Cerveza Artesanal IPA','Cerveza artesanal estilo India Pale Ale',85.00,'355ml',180,1,1,1,'2025-06-07 00:56:30','2025-06-07 00:56:30'),(5,'Limonada con Chía','Limonada natural con semillas de chía',55.00,'400ml',95,0,1,1,'2025-06-07 00:56:30','2025-06-07 00:56:30'),(6,'coca','',29.98,'355ml',3,0,0,1,'2025-06-07 07:13:52','2025-06-07 07:13:52');
/*!40000 ALTER TABLE `bebidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias_menu`
--

DROP TABLE IF EXISTS `categorias_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `tipo` enum('comida','bebida','postre') NOT NULL,
  `descripcion` text,
  `icono` varchar(10) DEFAULT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias_menu`
--

LOCK TABLES `categorias_menu` WRITE;
/*!40000 ALTER TABLE `categorias_menu` DISABLE KEYS */;
INSERT INTO `categorias_menu` VALUES (1,'Antojitos Oaxaquenos','comida','Platillos tradicionales y antojitos',NULL,'2025-06-05 20:27:27'),(2,'Platillos Principales','comida','Comidas fuertes tradicionales',NULL,'2025-06-05 20:27:27'),(3,'Caldos y Sopas','comida','Caldos y sopas tradicionales',NULL,'2025-06-05 20:27:27'),(4,'Mezcales Artesanales','bebida','Mezcales tradicionales de la region',NULL,'2025-06-05 20:27:27'),(5,'Aguas Frescas','bebida','Bebidas refrescantes naturales',NULL,'2025-06-05 20:27:27'),(6,'Bebidas Calientes','bebida','Bebidas tradicionales calientes',NULL,'2025-06-05 20:27:27'),(7,'Postres Tradicionales','postre','Postres de la cocina oaxaquena',NULL,'2025-06-05 20:27:27'),(8,'Nieves Artesanales','postre','Helados artesanales',NULL,'2025-06-05 20:27:27'),(9,'Dulces Regionales','postre','Dulces tipicos de Oaxaca',NULL,'2025-06-05 20:27:27');
/*!40000 ALTER TABLE `categorias_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  `precio` decimal(10,2) NOT NULL,
  `categoria_id` int DEFAULT NULL,
  `tipo` enum('comida','bebida','postre') NOT NULL,
  `disponible` tinyint(1) DEFAULT '1',
  `imagen_url` text,
  `picante` int DEFAULT '1',
  `ingredientes` json DEFAULT NULL,
  `especialidad` tinyint(1) DEFAULT '0',
  `tamaño` varchar(20) DEFAULT NULL,
  `alcohol` varchar(20) DEFAULT NULL,
  `artesanal` tinyint(1) DEFAULT '0',
  `porcion` varchar(50) DEFAULT NULL,
  `calorias` varchar(20) DEFAULT NULL,
  `dulzura` int DEFAULT '1',
  `casero` tinyint(1) DEFAULT '0',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `menu_items_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categorias_menu` (`id`) ON DELETE SET NULL,
  CONSTRAINT `menu_items_chk_1` CHECK ((`picante` between 1 and 3)),
  CONSTRAINT `menu_items_chk_2` CHECK ((`dulzura` between 1 and 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesas`
--

DROP TABLE IF EXISTS `mesas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` varchar(20) NOT NULL,
  `capacidad` int NOT NULL,
  `ubicacion` varchar(50) NOT NULL,
  `estado` enum('disponible','ocupada','reservada') DEFAULT 'disponible',
  `descripcion` text,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero` (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesas`
--

LOCK TABLES `mesas` WRITE;
/*!40000 ALTER TABLE `mesas` DISABLE KEYS */;
INSERT INTO `mesas` VALUES (1,'Mesa 1',4,'Terraza','ocupada','Mesa junto a la ventana','2025-06-05 20:27:27','2025-06-07 07:35:35'),(2,'Mesa 2',2,'Interior','disponible','Mesa romántica para parejas','2025-06-05 20:27:27','2025-06-05 20:27:27'),(3,'Mesa 3',6,'Salón principal','disponible','Mesa familiar grande','2025-06-05 20:27:27','2025-06-05 20:27:27'),(4,'Mesa 4',8,'Salón principal','disponible','Mesa para eventos especiales','2025-06-05 20:27:27','2025-06-05 20:27:27'),(5,'Mesa 5',4,'Terraza','disponible','Vista al jardín','2025-06-05 20:27:27','2025-06-05 20:27:27'),(6,'Mesa 6',2,'Interior','disponible','Mesa tranquila','2025-06-05 20:27:27','2025-06-05 20:27:27'),(7,'Mesa 7',4,'Salón principal','disponible','Mesa central','2025-06-05 20:27:27','2025-06-05 20:27:27'),(8,'Mesa 8',6,'Área VIP','disponible','Mesa premium','2025-06-05 20:27:27','2025-06-05 20:27:27'),(10,'mesa1',2,'Terraza','disponible','mesa ','2025-06-07 07:52:23','2025-06-07 07:52:23');
/*!40000 ALTER TABLE `mesas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden_items`
--

DROP TABLE IF EXISTS `orden_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orden_id` int NOT NULL,
  `menu_item_id` int NOT NULL,
  `cantidad` int NOT NULL DEFAULT '1',
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orden_id` (`orden_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `orden_items_ibfk_1` FOREIGN KEY (`orden_id`) REFERENCES `ordenes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `orden_items_ibfk_2` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden_items`
--

LOCK TABLES `orden_items` WRITE;
/*!40000 ALTER TABLE `orden_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `orden_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mesa_id` int NOT NULL,
  `mesero_id` int NOT NULL,
  `estado` enum('pendiente','en_preparacion','lista','entregada','cancelada') DEFAULT 'pendiente',
  `total` decimal(10,2) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `mesa_id` (`mesa_id`),
  KEY `mesero_id` (`mesero_id`),
  CONSTRAINT `ordenes_ibfk_1` FOREIGN KEY (`mesa_id`) REFERENCES `mesas` (`id`),
  CONSTRAINT `ordenes_ibfk_2` FOREIGN KEY (`mesero_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenes`
--

LOCK TABLES `ordenes` WRITE;
/*!40000 ALTER TABLE `ordenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postres`
--

DROP TABLE IF EXISTS `postres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_postre` varchar(100) NOT NULL,
  `descripcion` text,
  `precio_mxn` decimal(8,2) NOT NULL,
  `porcion` varchar(50) NOT NULL,
  `calorias` int DEFAULT NULL,
  `nivel_dulzura` varchar(10) DEFAULT 'Medio',
  `hecho_en_casa` tinyint(1) DEFAULT '0',
  `disponible_en_menu` tinyint(1) DEFAULT '1',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postres`
--

LOCK TABLES `postres` WRITE;
/*!40000 ALTER TABLE `postres` DISABLE KEYS */;
INSERT INTO `postres` VALUES (1,'Pastel de Chocolate','Delicioso pastel de chocolate con ganache',85.00,'Individual',320,'Alto',1,1,'2025-06-06 20:13:52','2025-06-06 20:13:52'),(2,'Flan Napolitano','Flan casero con caramelo',65.00,'Individual',220,'Medio',1,1,'2025-06-06 20:13:52','2025-06-06 20:13:52'),(3,'Helado de Vainilla','Helado cremoso de vainilla con trozos de galleta',45.00,'Individual',180,'Medio',0,1,'2025-06-06 20:13:52','2025-06-06 20:13:52'),(4,'Cheesecake','Cheesecake con salsa de frutos rojos',95.00,'Para compartir',350,'Medio',1,1,'2025-06-06 20:13:52','2025-06-06 20:13:52'),(5,'Brownie con Helado','Brownie caliente con helado de vainilla',75.00,'Individual',420,'Alto',1,1,'2025-06-06 20:13:52','2025-06-06 20:13:52'),(6,'flan','bebebe',99.97,'Individual',30,'Bajo',1,1,'2025-06-07 02:26:37','2025-06-07 02:26:37');
/*!40000 ALTER TABLE `postres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `rol` enum('mesero','administrador') NOT NULL,
  `estado` enum('activo','inactivo') DEFAULT 'activo',
  `turno` enum('matutino','vespertino','nocturno') DEFAULT NULL,
  `fecha_ingreso` date NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'pedro','pedro1311','Pedro Administrador','951-123-4567','administrador','activo',NULL,'2024-01-01','2025-06-05 20:27:27','2025-06-05 20:27:27'),(2,'admin','admin123','Administrador General','951-111-1111','administrador','activo',NULL,'2024-01-01','2025-06-05 20:27:27','2025-06-05 20:27:27'),(3,'juan.perez','juan123','Juan Perez','951-123-4567','mesero','activo','matutino','2024-01-15','2025-06-05 20:27:27','2025-06-05 20:27:27'),(4,'maria.gonzalez','maria123','Maria Gonzalez','951-987-6543','mesero','activo','vespertino','2024-02-01','2025-06-05 20:27:27','2025-06-05 20:27:27'),(5,'carlos.ruiz','carlos123','Carlos Ruiz','951-555-1234','mesero','activo','nocturno','2024-01-10','2025-06-05 20:27:27','2025-06-05 20:27:27'),(6,'ana.lopez','ana123','Ana Lopez','951-666-7777','mesero','activo','matutino','2024-02-15','2025-06-05 20:27:27','2025-06-05 20:27:27'),(7,'jose.martinez','jose123','Jose Martinez','951-888-9999','mesero','activo','vespertino','2024-03-01','2025-06-05 20:27:27','2025-06-05 20:27:27'),(8,'lucia.torres','lucia123','Lucia Torres','951-444-5555','mesero','activo','nocturno','2024-02-20','2025-06-05 20:27:27','2025-06-05 20:27:27');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-13 17:45:40
