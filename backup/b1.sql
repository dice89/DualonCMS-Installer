SET FOREIGN_KEY_CHECKS=0;DROP TABLE IF EXISTS configurations ;

CREATE TABLE `configurations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastName` varchar(30) DEFAULT NULL,
  `firstName` varchar(30) DEFAULT NULL,
  `eMail` varchar(60) DEFAULT NULL,
  `street` varchar(30) DEFAULT NULL,
  `houseNumber` int(4) DEFAULT NULL,
  `postCode` varchar(10) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `fax` varchar(30) DEFAULT NULL,
  `companyName` varchar(60) DEFAULT NULL,
  `legalForm` varchar(30) DEFAULT NULL,
  `vatId` varchar(30) DEFAULT NULL,
  `registerNumber` varchar(30) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `activeDesign` varchar(30) DEFAULT NULL,
  `activeTemplate` varchar(30) DEFAULT NULL,
  `modified` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS containers ;

CREATE TABLE `containers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `page_id` int(11) DEFAULT NULL,
  `layout_type_id` int(11) DEFAULT NULL,
  `column` int(1) NOT NULL,
  `order` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `layout_id` (`layout_type_id`),
  KEY `parent_id` (`parent_id`),
  KEY `page_id` (`page_id`),
  CONSTRAINT `containers_ibfk_3` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`) ON DELETE CASCADE,
  CONSTRAINT `containers_ibfk_1` FOREIGN KEY (`layout_type_id`) REFERENCES `layout_types` (`id`),
  CONSTRAINT `containers_ibfk_2` FOREIGN KEY (`parent_id`) REFERENCES `containers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;

INSERT INTO containers VALUES("71","0","22","0","0","0");
INSERT INTO containers VALUES("72","71","0","1","1","1");



DROP TABLE IF EXISTS content_values ;

CREATE TABLE `content_values` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_id` int(11) NOT NULL,
  `key` varchar(150) NOT NULL,
  `value` longtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `content_id` (`content_id`),
  KEY `plugin_view_value_id` (`key`),
  CONSTRAINT `content_values_ibfk_1` FOREIGN KEY (`content_id`) REFERENCES `contents` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS contents ;

CREATE TABLE `contents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `container_id` int(11) NOT NULL,
  `column` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `plugin_view_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `container_id` (`container_id`),
  KEY `plugin_id` (`plugin_view_id`),
  CONSTRAINT `contents_ibfk_4` FOREIGN KEY (`plugin_view_id`) REFERENCES `plugin_views` (`id`) ON DELETE CASCADE,
  CONSTRAINT `contents_ibfk_3` FOREIGN KEY (`container_id`) REFERENCES `containers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

INSERT INTO contents VALUES("27","72","1","1","6");



DROP TABLE IF EXISTS girokonten ;

CREATE TABLE `girokonten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankname` varchar(50) NOT NULL,
  `kontoname` varchar(50) NOT NULL,
  `gebuehr` text NOT NULL,
  `mindesteingang` text NOT NULL,
  `kreditkarte` varchar(50) NOT NULL,
  `guthabenzins` text NOT NULL,
  `sollzins` text NOT NULL,
  `bargeld` text NOT NULL,
  `kontoauszug` text NOT NULL,
  `sonstiges` text NOT NULL,
  `Link` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

INSERT INTO girokonten VALUES("4","DKB Deutsche Kredit Bank","DKB-Cash","0,00 EUR","0,00 EUR","VISA-Card und Partnerkarte kostenlos","0,5% auf dem Girokonto<br>\n2,55 % auf dem Tagesgeldkonto","7,9 %","kostenlos mit der DKB-VISA-Card","kostenlos (online)\n1,00 EUR (Post)","","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?4\" target=\"_blank\">www.dkb.de</a>");
INSERT INTO girokonten VALUES("5","Ing Diba ","Girokonto","0,00 EUR","0,00 EUR","VISA Direkt kostenlos","0,00%","9,5%","kostenlos (EC-Karte) an Automaten der Ing Diba und der Degussa Bank\n\nkostenlos (VISA-Karte) an allen VISA Geldautomaten in Ländern mit Euro-Währung ","kostenlos (online), Post (0,55 EUR)","0,50 EUR für jede VISA-Zahlung ab 50 EUR","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?5\" target=\"_blank\">www.ing-diba.de</a>");
INSERT INTO girokonten VALUES("3","comdirect","Girokonto","0,00 EUR","0,00 EUR","VISA Debitkarte kostenlos","0,00%","9,9%","kostenlos DE (EC-Karte):\n  Automaten der Cash Group\n\nkostenlos EU (VISA-Karte):\n  Automaten mit VISA-Symbol","kostenlos (online)","50EUR Prämie für Neukunden;\n\ninkl. Tagesgeldkonto","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?3\" target=\"_blank\">www.comdirect.de</a>");
INSERT INTO girokonten VALUES("6","VolkswagenBank","Volkswagen Girokonto","0,00 EUR","1000,00 EUR<br>\nsonst 4,50 EUR/Monat","Volkswagen VISA Card kostenlos","2,1% auf dem VISA-Card Konto","10,12%","kostenlos an VW-Bank-Automaten (EC)\n\nkostenlos mit der VISA-Card ","kostenlos (online)","mit der Volkswagen VISA Card 1% Rabatt beim Tanken\n\nbis zu 30 Tage zinsfreier Kredit durch bezahlen mit VISA-Card ","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?6\" target=\"_blank\">www.volkswagenbank.de</a>");
INSERT INTO girokonten VALUES("7","WüstenrotDirekt","Top Giro","0,00 EUR","0,00 EUR","MasterCard","0,66%","8,75%","kostenlos (EC): Automaten des Cashpool\n\n0,99 EUR pro Abhebung mit der MasterCard an Geldausgabeautomaten mit MasterCard-Logo ","kostenlos (Post)","","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?7\" target=\"_blank\">www.wuestenrotdirekt.de</a>");
INSERT INTO girokonten VALUES("8","netbank","giroLoyal","0,00 EUR<br>\nbei Nutzung als Gehaltskonto EC+MasterCard kostenlos","0,00 EUR","MasterCard","0,5 - 2,5%","9,50%","kostenlos mit EC-Karte bei Banken des CashPool\n\n5x kostenlos mit MasterCard","kostenlos (online),<br>\n0,55 EUR (Post)","","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?8\" target=\"_blank\">www.netbank.de</a>");
INSERT INTO girokonten VALUES("9","1822direkt","GiroAll","0,00 EUR","1200,00 EUR, sonst 3,90 EUR/Monat","MasterCard Daily Charge","bis zu 3,5%","10,25%	","kostenlos (europaweit) mit der MasterCard","kostenlos (online)<br>\n1,00 EUR (Post)","","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?8\" target=\"_blank\">www.1822direkt.de</a>");
INSERT INTO girokonten VALUES("10","DAB bank AG","Girokonto","0,00 EUR","1000,00 EUR,<br> sonst 4,00 EUR/Monat","MasterCard 10 EUR/Jahr","Bis 14.999,99 EUR 0,1% p.a.\nAb 15.000 EUR 1,0% p.a.","6,95%","kostenlos bei CashGroup-Automaten mit der EC-Karte","kostenlos (online),\nanfallendes Porto (Post)","","<a href=\"http://direktbankkonten.de/myphpfiles/redir.php?10\" target=\"_blank\">www.dab-bank.de</a>");



DROP TABLE IF EXISTS google_maps_locations ;

CREATE TABLE `google_maps_locations` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content_id` int(10) NOT NULL,
  `country` varchar(50) DEFAULT NULL,
  `zip_code` int(11) NOT NULL,
  `city` varchar(50) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `street_number` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS i18n ;

CREATE TABLE `i18n` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `locale` varchar(6) NOT NULL,
  `model` varchar(255) NOT NULL,
  `foreign_key` int(10) NOT NULL,
  `field` varchar(255) NOT NULL,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `locale` (`locale`),
  KEY `model` (`model`),
  KEY `row_id` (`foreign_key`),
  KEY `field` (`field`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS jos_banner ;

CREATE TABLE `jos_banner` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL DEFAULT '0',
  `type` varchar(30) NOT NULL DEFAULT 'banner',
  `name` varchar(255) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `imptotal` int(11) NOT NULL DEFAULT '0',
  `impmade` int(11) NOT NULL DEFAULT '0',
  `clicks` int(11) NOT NULL DEFAULT '0',
  `imageurl` varchar(100) NOT NULL DEFAULT '',
  `clickurl` varchar(200) NOT NULL DEFAULT '',
  `date` datetime DEFAULT NULL,
  `showBanner` tinyint(1) NOT NULL DEFAULT '0',
  `checked_out` tinyint(1) NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `editor` varchar(50) DEFAULT NULL,
  `custombannercode` text,
  `catid` int(10) unsigned NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `sticky` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `publish_up` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `tags` text NOT NULL,
  `params` text NOT NULL,
  PRIMARY KEY (`bid`),
  KEY `viewbanner` (`showBanner`),
  KEY `idx_banner_catid` (`catid`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO jos_banner VALUES("1","1","","OSM 1","osm-1","0","43","0","osmbanner1.png","http://www.opensourcematters.org","2009-05-05 15:19:30","1","62","2009-05-05 15:30:44","","","13","","0","1","0000-00-00 00:00:00","0000-00-00 00:00:00","","width=0\nheight=0");
INSERT INTO jos_banner VALUES("2","1","banner","OSM 2","osm-2","0","49","0","osmbanner2.png","http://www.opensourcematters.org","2004-07-07 15:31:29","1","0","0000-00-00 00:00:00","","","13","","0","2","0000-00-00 00:00:00","0000-00-00 00:00:00","","");
INSERT INTO jos_banner VALUES("4","1","","JoomlaCode","joomlacode","0","38","0","","http://joomlacode.org","2006-05-29 14:19:26","1","0","0000-00-00 00:00:00","","<a href=\"{CLICKURL}\" target=\"_blank\">{NAME}</a>\n<br/>\nJoomlaCode - Entwicklung und Verbreitung einfach gemacht.","14","","0","2","0000-00-00 00:00:00","0000-00-00 00:00:00","","");



DROP TABLE IF EXISTS jos_bannerclient ;

CREATE TABLE `jos_bannerclient` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `contact` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `extrainfo` text NOT NULL,
  `checked_out` tinyint(1) NOT NULL DEFAULT '0',
  `checked_out_time` time DEFAULT NULL,
  `editor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO jos_bannerclient VALUES("1","Open Source Matters","Administrator","admin@opensourcematters.org","","62","15:30:33","");
INSERT INTO jos_bannerclient VALUES("2","affili.net","affili.net","info@affili.net","","0","00:00:00","");



DROP TABLE IF EXISTS jos_bannertrack ;

CREATE TABLE `jos_bannertrack` (
  `track_date` date NOT NULL,
  `track_type` int(10) unsigned NOT NULL,
  `banner_id` int(10) unsigned NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_categories ;

CREATE TABLE `jos_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `image` varchar(255) NOT NULL DEFAULT '',
  `section` varchar(50) NOT NULL DEFAULT '',
  `image_position` varchar(30) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `editor` varchar(50) DEFAULT NULL,
  `ordering` int(11) NOT NULL DEFAULT '0',
  `access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `count` int(11) NOT NULL DEFAULT '0',
  `params` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cat_idx` (`section`,`published`,`access`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

INSERT INTO jos_categories VALUES("1","0","Aktuell","","aktuelle-nachrichten","taking_notes.jpg","1","left","Die aktuellen Nachrichten vom Joomla!-Team","1","62","2009-05-05 16:38:14","","1","0","1","");
INSERT INTO jos_categories VALUES("2","0","Links rund um Joomla!","","links-rund-um-joomla","clock.jpg","com_weblinks","left","Eine Auswahl an Weblinks, die mit dem Joomla!-Projekt zu tun haben.","1","0","0000-00-00 00:00:00","","1","0","0","");
INSERT INTO jos_categories VALUES("3","0","Kurzmeldungen","","kurzmeldungen","","1","left","","1","0","0000-00-00 00:00:00","","2","0","0","");
INSERT INTO jos_categories VALUES("4","0","Joomla!","","joomla","","com_newsfeeds","left","","1","0","0000-00-00 00:00:00","","2","0","0","");
INSERT INTO jos_categories VALUES("5","0","Freie und Open Source-Software","","freie-und-open-source-software","","com_newsfeeds","left","Lesen Sie das Neueste über Freie und Open Source Software von einigen der führenden Verfechtern.","1","0","0000-00-00 00:00:00","","3","0","0","");
INSERT INTO jos_categories VALUES("6","0","Related Projects","","related-projects","","com_newsfeeds","left","Joomla basiert auf andere Freie und Open Source Projekte und arbeitet mit vielen zusammen. Wir haben einige ausgewählt, damit Sie auf dem Laufenden bleiben.","1","0","0000-00-00 00:00:00","","4","0","0","");
INSERT INTO jos_categories VALUES("12","0","Kontakte","","kontakte","","com_contact_details","left","Details zu den Kontakten dieser Webseite","1","0","0000-00-00 00:00:00","","0","0","0","");
INSERT INTO jos_categories VALUES("13","0","Joomla","","joomla","","com_banner","left","","1","62","2009-05-05 15:30:26","","0","0","0","");
INSERT INTO jos_categories VALUES("14","0","Text-Werbung","","text-werbung","","com_banner","left","","1","0","0000-00-00 00:00:00","","0","0","0","");
INSERT INTO jos_categories VALUES("15","0","Merkmale","","merkmale","","com_content","left","","0","0","0000-00-00 00:00:00","","6","0","0","");
INSERT INTO jos_categories VALUES("17","0","Benefits","","benefits","","com_content","left","","0","0","0000-00-00 00:00:00","","4","0","0","");
INSERT INTO jos_categories VALUES("18","0","Plattformen","","plattformen","","com_content","left","","0","0","0000-00-00 00:00:00","","3","0","0","");
INSERT INTO jos_categories VALUES("19","0","Andere Quellen","","andere-quellen","","com_weblinks","left","","1","0","0000-00-00 00:00:00","","2","0","0","");
INSERT INTO jos_categories VALUES("35","0","Tagesgeld","","tagesgeld","","com_newsfeeds","left","","1","62","2009-05-05 16:36:32","","5","0","0","");
INSERT INTO jos_categories VALUES("34","0","Girokonten","","girokonten","","5","left","Vergleich der besten deutschen Girokonten der Direktbanken","1","62","2009-05-05 15:31:27","","1","0","0","");
INSERT INTO jos_categories VALUES("33","0","Joomla! Promo","","joomla-promo","","com_banner","left","","1","62","2009-05-05 15:30:22","","1","0","0","");



DROP TABLE IF EXISTS jos_components ;

CREATE TABLE `jos_components` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `link` varchar(255) NOT NULL DEFAULT '',
  `menuid` int(11) unsigned NOT NULL DEFAULT '0',
  `parent` int(11) unsigned NOT NULL DEFAULT '0',
  `admin_menu_link` varchar(255) NOT NULL DEFAULT '',
  `admin_menu_alt` varchar(255) NOT NULL DEFAULT '',
  `option` varchar(50) NOT NULL DEFAULT '',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `admin_menu_img` varchar(255) NOT NULL DEFAULT '',
  `iscore` tinyint(4) NOT NULL DEFAULT '0',
  `params` text NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `parent_option` (`parent`,`option`(32))
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

INSERT INTO jos_components VALUES("1","Banner","","0","0","","Banner-Verwaltung","com_banners","0","js/ThemeOffice/component.png","0","track_impressions=0\ntrack_clicks=0\ntag_prefix=\n\n","1");
INSERT INTO jos_components VALUES("2","Banner","","0","1","option=com_banners","Aktive Banner","com_banners","1","js/ThemeOffice/edit.png","0","","1");
INSERT INTO jos_components VALUES("3","Kunden","","0","1","option=com_banners&c=client","Kunden","com_banners","2","js/ThemeOffice/categories.png","0","","1");
INSERT INTO jos_components VALUES("4","Weblinks","option=com_weblinks","0","0","","Weblinks","com_weblinks","0","js/ThemeOffice/component.png","0","show_comp_description=1\ncomp_description=\nshow_link_hits=1\nshow_link_description=1\nshow_other_cats=1\nshow_headings=1\nshow_page_title=1\nlink_target=0\nlink_icons=\n\n","1");
INSERT INTO jos_components VALUES("5","Links","","0","4","option=com_weblinks","Existierende Weblinks anzeigen","com_weblinks","1","js/ThemeOffice/edit.png","0","","1");
INSERT INTO jos_components VALUES("6","Kategorien","","0","4","option=com_categories&section=com_weblinks","Weblink-Kategorien","","2","js/ThemeOffice/categories.png","0","","1");
INSERT INTO jos_components VALUES("7","Kontakte","option=com_contact","0","0","","Kontaktdetails bearbeiten","com_contact","0","js/ThemeOffice/component.png","1","contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n","1");
INSERT INTO jos_components VALUES("8","Kontakte","","0","7","option=com_contact","Kontaktdetails bearbeiten","com_contact","0","js/ThemeOffice/edit.png","1","","1");
INSERT INTO jos_components VALUES("9","Kategorien","","0","7","option=com_categories&section=com_contact_details","Kontaktkategorien","","2","js/ThemeOffice/categories.png","1","contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n","1");
INSERT INTO jos_components VALUES("10","Umfragen","option=com_poll","0","0","option=com_poll","Umfragen","com_poll","0","js/ThemeOffice/component.png","0","","1");
INSERT INTO jos_components VALUES("11","Newsfeeds","option=com_newsfeeds","0","0","","Newsfeeds","com_newsfeeds","0","js/ThemeOffice/component.png","0","","1");
INSERT INTO jos_components VALUES("12","Feeds","","0","11","option=com_newsfeeds","Feeds","com_newsfeeds","1","js/ThemeOffice/edit.png","0","show_headings=1\nshow_name=1\nshow_articles=1\nshow_link=1\nshow_cat_description=1\nshow_cat_items=1\nshow_feed_image=1\nshow_feed_description=1\nshow_item_description=1\nfeed_word_count=0\n\n","1");
INSERT INTO jos_components VALUES("13","Kategorien","","0","11","option=com_categories&section=com_newsfeeds","Kategorien","","2","js/ThemeOffice/categories.png","0","","1");
INSERT INTO jos_components VALUES("14","Benutzer","option=com_user","0","0","","","com_user","0","","1","","1");
INSERT INTO jos_components VALUES("15","Suche","option=com_search","0","0","option=com_search","Statistiken der Suchanfragen","com_search","0","js/ThemeOffice/component.png","1","enabled=0\n\n","1");
INSERT INTO jos_components VALUES("16","Kategorien","","0","1","option=com_categories&section=com_banner","Kategorien","","3","","1","","1");
INSERT INTO jos_components VALUES("17","Wrapper","option=com_wrapper","0","0","","Wrapper","com_wrapper","0","","1","","1");
INSERT INTO jos_components VALUES("18","Mail an","","0","0","","","com_mailto","0","","1","","1");
INSERT INTO jos_components VALUES("19","Medien","","0","0","option=com_media","Medien","com_media","0","","1","upload_extensions=bmp,csv,doc,epg,gif,ico,jpg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,EPG,GIF,ICO,JPG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS\nupload_maxsize=10000000\nfile_path=images\nimage_path=images/stories\nrestrict_uploads=1\ncheck_mime=1\nimage_extensions=bmp,gif,jpg,png\nignore_extensions=\nupload_mime=image/jpeg,image/gif,image/png,image/bmp,application/x-shockwave-flash,application/msword,application/excel,application/pdf,application/powerpoint,text/plain,application/x-zip\nupload_mime_illegal=text/html\nenable_flash=0\n\n","1");
INSERT INTO jos_components VALUES("20","Beiträge","option=com_content","0","0","","","com_content","0","","1","show_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\nfeed_summary=0\n\n","1");
INSERT INTO jos_components VALUES("21","Konfiguration","","0","0","","Konfiguration","com_config","0","","1","","1");
INSERT INTO jos_components VALUES("22","Installation","","0","0","","Installer","com_installer","0","","1","","1");
INSERT INTO jos_components VALUES("23","Sprachen","","0","0","","Sprachen","com_languages","0","","1","administrator=de-DE\nsite=de-DE","1");
INSERT INTO jos_components VALUES("24","Massenmail","","0","0","","Massenmail","com_massmail","0","","1","mailSubjectPrefix=\nmailBodySuffix=\n\n","1");
INSERT INTO jos_components VALUES("25","Menüeditor","","0","0","","Menüeditor","com_menus","0","","1","","1");
INSERT INTO jos_components VALUES("27","Nachrichten","","0","0","","Nachrichten","com_messages","0","","1","","1");
INSERT INTO jos_components VALUES("28","Module","","0","0","","Module","com_modules","0","","1","","1");
INSERT INTO jos_components VALUES("29","Plugins","","0","0","","Plugins","com_plugins","0","","1","","1");
INSERT INTO jos_components VALUES("30","Templates","","0","0","","Templates","com_templates","0","","1","","1");
INSERT INTO jos_components VALUES("31","Benutzer","","0","0","","Benutzer","com_users","0","","1","allowUserRegistration=0\nnew_usertype=Registered\nuseractivation=0\nfrontend_userparams=0\n\n","1");
INSERT INTO jos_components VALUES("32","Cache","","0","0","","Cache","com_cache","0","","1","","1");
INSERT INTO jos_components VALUES("33","Kontrollzentrum","","0","0","","Kontrollzentrum","com_cpanel","0","","1","","1");
INSERT INTO jos_components VALUES("34","Impressum","option=com_impressum","0","0","option=com_impressum","Impressum","com_impressum","0","components/com_impressum/assets/images/recht.png","0","","1");



DROP TABLE IF EXISTS jos_contact_details ;

CREATE TABLE `jos_contact_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `con_position` varchar(255) DEFAULT NULL,
  `address` text,
  `suburb` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `postcode` varchar(100) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `misc` mediumtext,
  `image` varchar(255) DEFAULT NULL,
  `imagepos` varchar(20) DEFAULT NULL,
  `email_to` varchar(255) DEFAULT NULL,
  `default_con` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `published` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `params` text NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `catid` int(11) NOT NULL DEFAULT '0',
  `access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `mobile` varchar(255) NOT NULL DEFAULT '',
  `webpage` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `catid` (`catid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO jos_contact_details VALUES("1","Name","name","Position","Straße","Stadt/Bezirk","Bundesland/Kanton","Staat","PLZ","Telefon","Fax","Weitere Infos","powered_by.png","top","email@email.com","1","1","0","0000-00-00 00:00:00","1","show_name=1\nshow_position=1\nshow_email=0\nshow_street_address=1\nshow_suburb=1\nshow_state=1\nshow_postcode=1\nshow_country=1\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nshow_webpage=1\nshow_misc=1\nshow_image=1\nallow_vcard=0\ncontact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_email_form=1\nemail_description=1\nshow_email_copy=1\nbanned_email=\nbanned_subject=\nbanned_text=","0","12","0","","");



DROP TABLE IF EXISTS jos_content ;

CREATE TABLE `jos_content` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `title_alias` varchar(255) NOT NULL DEFAULT '',
  `introtext` mediumtext NOT NULL,
  `fulltext` mediumtext NOT NULL,
  `state` tinyint(3) NOT NULL DEFAULT '0',
  `sectionid` int(11) unsigned NOT NULL DEFAULT '0',
  `mask` int(11) unsigned NOT NULL DEFAULT '0',
  `catid` int(11) unsigned NOT NULL DEFAULT '0',
  `created` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created_by` int(11) unsigned NOT NULL DEFAULT '0',
  `created_by_alias` varchar(255) NOT NULL DEFAULT '',
  `modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified_by` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `publish_up` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `images` text NOT NULL,
  `urls` text NOT NULL,
  `attribs` text NOT NULL,
  `version` int(11) unsigned NOT NULL DEFAULT '1',
  `parentid` int(11) unsigned NOT NULL DEFAULT '0',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `access` int(11) unsigned NOT NULL DEFAULT '0',
  `hits` int(11) unsigned NOT NULL DEFAULT '0',
  `metadata` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_section` (`sectionid`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_state` (`state`),
  KEY `idx_catid` (`catid`),
  KEY `idx_createdby` (`created_by`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

INSERT INTO jos_content VALUES("2","Kurzmeldung 1","kurzmeldung-1","","<p>Mit Joomla! ist es einfach eine beliebige Website zu erstellen. Was immer Sie machen möchten, ob es nur eine Web-Visitenkarte werden soll oder ob Sie eine große Online-Community erstellen möchten, Joomla! erlaubt Ihnen, Ihre Träume in wenigen Minuten zu verwirklichen und bietet Ihnen die Möglichkeit Funktionalitäten da einzusetzen, wo Sie sie brauchen. Die Vielzahl an Erweiterungen wird Ihrer Site erlauben zu wachsen und Ihren Besuchern neue Funktionen bieten, die Ihr Ansehen im gesamten Internet erhöhen kann.</p>","","0","1","0","3","2004-08-10 06:30:34","62","","2008-05-05 01:05:03","62","62","2009-05-07 11:32:27","2004-08-09 10:00:00","0000-00-00 00:00:00","","","show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=","6","0","3","","","0","2","robots=\nauthor=");
INSERT INTO jos_content VALUES("46","Girokonten Vergleich","vergleich1","","<p>Hier finden sie die aktuellen Top-Angebote deutscher DirektBanken:</p><p>&nbsp;</p><p>{rdaddphp file=myphpfiles/konten.php}</p>","","1","5","0","34","2009-05-05 15:31:47","62","","2009-05-07 14:58:31","62","0","0000-00-00 00:00:00","2009-05-05 15:31:47","0000-00-00 00:00:00","","","show_title=0\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=0\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=0\nshow_print_icon=0\nshow_email_icon=\nlanguage=de-DE\nkeyref=\nreadmore=","10","0","2","","","0","248","robots=\nauthor=");
INSERT INTO jos_content VALUES("47","4% aufs Tagesgeld bei Comdirect","comdirekttagesgeldaktion","","<p>Bis zum 31.8.2009 gibts bei comdirect Tagesgeld Plus mit 4% Verzinsung für Neukunden bis 10000 EUR!</p><p>Jetzt abschließen und sich diese aktuellen TopKonditionen sichern. </p><p><a href=\"myphpfiles/redir.php?3\" target=\"_blank\" title=\"comdirekt\">www.comdirect.de</a></p>","","1","1","0","1","2009-05-05 16:49:20","62","","2009-05-11 21:20:25","62","0","0000-00-00 00:00:00","2009-05-05 16:49:20","0000-00-00 00:00:00","","","show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=","4","0","1","","","0","20","robots=\nauthor=");
INSERT INTO jos_content VALUES("43","Beispielseiten und Menülinks","beispielseiten-und-menuelinks","","<p>Diese Seite dient als Beispiel für einen Beitrag der \"nicht kategorisiert\" ist, d.h. er gehört keinem Bereich und keiner Kategorie an. Wie Sie sehen gibt es hier auf der linken Seite ein neues Menü. Es enthält Links zu denselben Inhalten, die jedoch in vier Varianten dargestellt werden:</p><ul><li>ein Bereich als Blog</li><li>ein Bereich als Tabelle</li><li>eine Kategorie als Blog</li><li>eine Kategorie als Tabelle</li></ul><p>Folgen Sie den Links im Menü <strong>Beispielseiten</strong>, um einige Möglichkeiten zu sehen, wie sich die verschiedenen Inhalte innerhalb der Grundinstallation von Joomla! darstellen lassen.</p><p>Darin sind Komponenten und einzelne Beiträge eingeschlossen. Die Links oder Menüeintrags-Typen werden gemeinsam über <strong><code>Menüs-&gt;[menüname]-&gt;Menüeinträge</code></strong> verwaltet.</p>","","0","0","0","0","2006-10-12 09:26:52","62","","2008-05-12 18:32:57","62","0","0000-00-00 00:00:00","2006-10-11 10:00:00","0000-00-00 00:00:00","","","show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=","8","0","2","Nicht kategorisiert, unkategorisiert, Beispielseiten und Menülinks","","0","42","robots=\nauthor=");
INSERT INTO jos_content VALUES("48","Direktbanken","direktbanken","","Direktbanken sind Banken, die nicht über ein eigenes Filialnetz verfügen. Trotzdem unterliegen sie den gleichen bankenaufsichtsrechtlichen Bestimmungen wie Filialbanken.<br /><br />Durch die Einsparung von Personal- und Filialunterhaltungskosten bieten diese Banken in finanzieller Hinsicht oft bessere Konditionen an als ihre lokale Konkurrenz, im Gegenzug muss man allerdings auf lokale Ansprechpartner verzichten und seine Bankgeschäfte via Telefon, Internet oder über den Postverkehr regeln. Wem dies keine Probleme bereitet, findet hier eine aktuelle Übersicht über die verschiedenen Konten der bekanntesten in Deutschland fungierenden Direktbanken.","","1","5","0","34","2009-05-07 14:57:06","62","","2009-05-11 21:18:12","62","0","0000-00-00 00:00:00","2009-05-07 14:57:06","0000-00-00 00:00:00","","","show_title=\nlink_titles=\nshow_intro=\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_vote=\nshow_author=0\nshow_create_date=0\nshow_modify_date=0\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=","2","0","1","","","0","32","robots=\nauthor=");
INSERT INTO jos_content VALUES("49","Bankenverbünde","bankenverbuende","","<h1>Bankenverbünde</h1><p>Direktbanken haben oft kein eigenes Filialnetz, was die Bargeldverfügung schwierig oder teuer macht. Hier gibt es allerdings eine Übersicht über die bekanntesten deutschen Verbünde. Bei einem der genannten kann man meistens kostenlos Bargeld mit der EC-/Maestrokarte beziehen, wenn man dies nicht sowieso über eine enthaltene Kreditkarte bei allen Banken erledigen kann.</p><p>Hier eine Übersicht über die Verbünde </p><h3>Sparkassen: </h3><blockquote>Deutschlandweit gibt es ca. 24.600 Geldautomaten von Sparkassen, die für Sparkassenkunden kostenlos genutzt werden können.</blockquote><p>&nbsp;</p><h3>Genossenschaftsbanken (VR-Banken): </h3><blockquote>Als Kunde der Genossenschaftsbanken (hauptsächlich Volks- und Raiffeisenbanken) hat man Zugang zu kostenlosen Bargeldverfügungen an etwa 18.000 Automaten.</blockquote> <h3>Cash-Group: </h3><blockquote>Zur Cash-Grop gehören die Deutsche Bank, Dresdner Bank, Commerzbank, HypoVereinsbank, Postbank und deren Töchter. Sie bietet Zugang zu rund 7000 Autmaten.</blockquote><p>&nbsp;</p><h3>CashPool: </h3><blockquote>CashPool ist ein Zusammenschluss mehrerer deutscher Banken, gegründet auf Initiative der SEB, der Citibank und der Sparda-Banken. Zum CashPool gehören ca. 2500 Geldautomaten. </blockquote><p>&nbsp;</p>","","1","0","0","0","2009-05-11 20:51:24","62","","2009-05-11 21:17:51","62","0","0000-00-00 00:00:00","2009-05-11 20:51:24","0000-00-00 00:00:00","","","show_title=1\nlink_titles=\nshow_intro=\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_vote=0\nshow_author=0\nshow_create_date=0\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=de-DE\nkeyref=\nreadmore=","7","0","1","","","0","122","robots=\nauthor=");



DROP TABLE IF EXISTS jos_content_frontpage ;

CREATE TABLE `jos_content_frontpage` (
  `content_id` int(11) NOT NULL DEFAULT '0',
  `ordering` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO jos_content_frontpage VALUES("48","1");



DROP TABLE IF EXISTS jos_content_rating ;

CREATE TABLE `jos_content_rating` (
  `content_id` int(11) NOT NULL DEFAULT '0',
  `rating_sum` int(11) unsigned NOT NULL DEFAULT '0',
  `rating_count` int(11) unsigned NOT NULL DEFAULT '0',
  `lastip` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_core_acl_aro ;

CREATE TABLE `jos_core_acl_aro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_value` varchar(240) NOT NULL DEFAULT '0',
  `value` varchar(240) NOT NULL DEFAULT '',
  `order_value` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `hidden` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `jos_section_value_value_aro` (`section_value`(100),`value`(100)),
  KEY `jos_gacl_hidden_aro` (`hidden`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

INSERT INTO jos_core_acl_aro VALUES("10","users","62","0","Administrator","0");



DROP TABLE IF EXISTS jos_core_acl_aro_groups ;

CREATE TABLE `jos_core_acl_aro_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `lft` int(11) NOT NULL DEFAULT '0',
  `rgt` int(11) NOT NULL DEFAULT '0',
  `value` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `jos_gacl_parent_id_aro_groups` (`parent_id`),
  KEY `jos_gacl_lft_rgt_aro_groups` (`lft`,`rgt`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

INSERT INTO jos_core_acl_aro_groups VALUES("17","0","ROOT","1","22","ROOT");
INSERT INTO jos_core_acl_aro_groups VALUES("28","17","USERS","2","21","USERS");
INSERT INTO jos_core_acl_aro_groups VALUES("29","28","Public Frontend","3","12","Public Frontend");
INSERT INTO jos_core_acl_aro_groups VALUES("18","29","Registered","4","11","Registered");
INSERT INTO jos_core_acl_aro_groups VALUES("19","18","Author","5","10","Author");
INSERT INTO jos_core_acl_aro_groups VALUES("20","19","Editor","6","9","Editor");
INSERT INTO jos_core_acl_aro_groups VALUES("21","20","Publisher","7","8","Publisher");
INSERT INTO jos_core_acl_aro_groups VALUES("30","28","Public Backend","13","20","Public Backend");
INSERT INTO jos_core_acl_aro_groups VALUES("23","30","Manager","14","19","Manager");
INSERT INTO jos_core_acl_aro_groups VALUES("24","23","Administrator","15","18","Administrator");
INSERT INTO jos_core_acl_aro_groups VALUES("25","24","Super Administrator","16","17","Super Administrator");



DROP TABLE IF EXISTS jos_core_acl_aro_map ;

CREATE TABLE `jos_core_acl_aro_map` (
  `acl_id` int(11) NOT NULL DEFAULT '0',
  `section_value` varchar(230) NOT NULL DEFAULT '0',
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`acl_id`,`section_value`,`value`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_core_acl_aro_sections ;

CREATE TABLE `jos_core_acl_aro_sections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(230) NOT NULL DEFAULT '',
  `order_value` int(11) NOT NULL DEFAULT '0',
  `name` varchar(230) NOT NULL DEFAULT '',
  `hidden` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `jos_gacl_value_aro_sections` (`value`),
  KEY `jos_gacl_hidden_aro_sections` (`hidden`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

INSERT INTO jos_core_acl_aro_sections VALUES("10","users","1","Users","0");



DROP TABLE IF EXISTS jos_core_acl_groups_aro_map ;

CREATE TABLE `jos_core_acl_groups_aro_map` (
  `group_id` int(11) NOT NULL DEFAULT '0',
  `section_value` varchar(240) NOT NULL DEFAULT '',
  `aro_id` int(11) NOT NULL DEFAULT '0',
  UNIQUE KEY `group_id_aro_id_groups_aro_map` (`group_id`,`section_value`,`aro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO jos_core_acl_groups_aro_map VALUES("25","","10");



DROP TABLE IF EXISTS jos_core_log_items ;

CREATE TABLE `jos_core_log_items` (
  `time_stamp` date NOT NULL DEFAULT '0000-00-00',
  `item_table` varchar(50) NOT NULL DEFAULT '',
  `item_id` int(11) unsigned NOT NULL DEFAULT '0',
  `hits` int(11) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_core_log_searches ;

CREATE TABLE `jos_core_log_searches` (
  `search_term` varchar(128) NOT NULL DEFAULT '',
  `hits` int(11) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_groups ;

CREATE TABLE `jos_groups` (
  `id` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO jos_groups VALUES("0","Public");
INSERT INTO jos_groups VALUES("1","Registered");
INSERT INTO jos_groups VALUES("2","Special");



DROP TABLE IF EXISTS jos_impressum ;

CREATE TABLE `jos_impressum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firma` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `name1` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `name2` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `name3` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `name4` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `strasse` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `plz` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `ort` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `land` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `telefon` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `fax` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `handy` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `website` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `email` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `blz` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `bankname` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `kontonr` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `iban` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `swift` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `vertreter` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `registergericht` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `registernummer` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `steuernummer` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `technikperson` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `technikwebsite` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `technikemail` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `inhaltperson` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `inhaltemail` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `templatename` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `templatehersteller` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `templatewebsite` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `version` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `image` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT '',
  `misc` text COLLATE latin1_german1_ci NOT NULL,
  `params` text COLLATE latin1_german1_ci NOT NULL,
  `bildrechte` text COLLATE latin1_german1_ci NOT NULL,
  `bildquellen` text COLLATE latin1_german1_ci NOT NULL,
  `adresstitel` varchar(255) COLLATE latin1_german1_ci NOT NULL DEFAULT 'Firmeninformationen',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

INSERT INTO jos_impressum VALUES("1","","Benedikt Steffan","","","","Flürlein 5","97941","Tauberbischofsheim","Deutschland","","","","","info@direktbankkonten.de","","","","","","","","","","","","","Benedikt Steffan","b.steffan@gratis-toolz.de","","","","impressum developed by <a href=\"http://www.gcsoft.de\">gcsoft.de</a> version 2.0","gc-soft.gif","Alle hier verwendeten Namen, Begriffe, Zeichen und Grafiken können Marken- oder Warenzeichen im Besitze ihrer rechtlichen Eigentümer sein. Die Rechte aller erwähnten und benutzten Marken- und Warenzeichen liegen ausschließlich bei deren Besitzern.","show_info=1\nshow_image=0\npos_image=0\nshow_bank=0\nshow_recht=1\nshow_technik=0\nshow_design=0\nshow_links=1\nshow_google=1\nshow_bildrechte=0\nshow_copyright=1\nshow_icons=0\ncontact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_mobile=\nicon_fax=","","","Impressum");



DROP TABLE IF EXISTS jos_menu ;

CREATE TABLE `jos_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menutype` varchar(75) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `alias` varchar(255) NOT NULL DEFAULT '',
  `link` text,
  `type` varchar(50) NOT NULL DEFAULT '',
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `parent` int(11) unsigned NOT NULL DEFAULT '0',
  `componentid` int(11) unsigned NOT NULL DEFAULT '0',
  `sublevel` int(11) DEFAULT '0',
  `ordering` int(11) DEFAULT '0',
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `pollid` int(11) NOT NULL DEFAULT '0',
  `browserNav` tinyint(4) DEFAULT '0',
  `access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `utaccess` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `params` text NOT NULL,
  `lft` int(11) unsigned NOT NULL DEFAULT '0',
  `rgt` int(11) unsigned NOT NULL DEFAULT '0',
  `home` int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `componentid` (`componentid`,`menutype`,`published`,`access`),
  KEY `menutype` (`menutype`)
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

INSERT INTO jos_menu VALUES("1","mainmenu","Startseite","home","index.php?option=com_content&view=frontpage","component","1","0","20","0","1","0","0000-00-00 00:00:00","0","0","0","3","show_page_title=1\npage_title=Willkommen auf der Startseite\nshow_description=0\nshow_description_image=0\nnum_leading_articles=1\nnum_intro_articles=4\nnum_columns=2\nnum_links=4\nshow_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\norderby_pri=\norderby_sec=front\nshow_pagination=2\nshow_pagination_results=1\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","1");
INSERT INTO jos_menu VALUES("2","mainmenu","Joomla!-Lizenz","joomla-lizenz","index.php?option=com_content&view=article&id=5","component","0","0","20","0","3","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("41","mainmenu","FAQ","faq","index.php?option=com_content&view=section&id=3","component","0","0","20","0","5","0","0000-00-00 00:00:00","0","0","0","0","show_page_title=1\nshow_description=0\nshow_description_image=0\nshow_categories=1\nshow_empty_categories=0\nshow_cat_num_articles=1\nshow_category_description=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\norderby=\nshow_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1","0","0","0");
INSERT INTO jos_menu VALUES("18","topmenu","News","news","index.php?option=com_newsfeeds&view=newsfeed&id=1&feedid=1","component","0","0","11","0","3","0","0000-00-00 00:00:00","0","0","0","3","show_page_title=1\npage_title=News\npageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_headings=1\nshow_name=1\nshow_articles=1\nshow_link=1\nshow_other_cats=1\nshow_cat_description=1\nshow_cat_items=1\nshow_feed_image=1\nshow_feed_description=1\nshow_item_description=1\nfeed_word_count=0\n\n","0","0","0");
INSERT INTO jos_menu VALUES("20","usermenu","Ihre Details","ihre-details","index.php?option=com_user&view=user&task=edit","component","1","0","14","0","1","0","0000-00-00 00:00:00","0","0","1","3","","0","0","0");
INSERT INTO jos_menu VALUES("24","usermenu","Abmelden","abmelden","index.php?option=com_user&view=login","component","1","0","14","0","4","0","0000-00-00 00:00:00","0","0","1","3","","0","0","0");
INSERT INTO jos_menu VALUES("38","keyconcepts","Inhaltlayouts","inhaltlayouts","index.php?option=com_content&view=article&id=24","component","0","0","20","0","2","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("27","mainmenu","Joomla! im Überblick","joomla-overview","index.php?option=com_content&view=article&id=19","component","0","0","20","0","2","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("28","topmenu","Über Joomla!","about-joomla","index.php?option=com_content&view=article&id=25","component","0","0","20","0","1","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("29","topmenu","Merkmale","merkmale","index.php?option=com_content&view=article&id=22","component","0","0","20","0","2","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("30","topmenu","Die Community","die-community","index.php?option=com_content&view=article&id=27","component","0","0","20","0","4","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("34","mainmenu","Was ist neu in 1.5?","was-ist-neu-in-1-5","index.php?option=com_content&view=article&id=22","component","0","27","20","1","1","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("40","keyconcepts","Erweiterungen","erweiterungen","index.php?option=com_content&view=article&id=26","component","0","0","20","0","1","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("37","mainmenu","Mehr über Joomla!","mehr-ueber-joomla","index.php?option=com_content&view=section&id=4","component","0","0","20","0","4","0","0000-00-00 00:00:00","0","0","0","0","show_page_title=1\nshow_description=0\nshow_description_image=0\nshow_categories=1\nshow_empty_categories=0\nshow_cat_num_articles=1\nshow_category_description=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\norderby=\nshow_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1","0","0","0");
INSERT INTO jos_menu VALUES("43","keyconcepts","Beispielseiten","beispielseiten","index.php?option=com_content&view=article&id=43","component","0","0","20","0","3","0","0000-00-00 00:00:00","0","0","0","0","pageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("48","mainmenu","Weblinks","weblinks","index.php?option=com_weblinks&view=categories","component","1","0","4","0","9","0","0000-00-00 00:00:00","0","0","0","0","page_title=Weblinks\nimage=-1\nimage_align=right\npageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_comp_description=1\ncomp_description=\nshow_link_hits=1\nshow_link_description=1\nshow_other_cats=1\nshow_headings=1\nshow_page_title=1\nlink_target=0\nlink_icons=\n\n","0","0","0");
INSERT INTO jos_menu VALUES("49","mainmenu","Newsfeeds","newsfeeds","index.php?option=com_newsfeeds&view=categories","component","1","0","11","0","10","0","0000-00-00 00:00:00","0","0","0","0","show_page_title=1\npage_title=Newsfeeds\nshow_comp_description=1\ncomp_description=\nimage=-1\nimage_align=right\npageclass_sfx=\nmenu_image=-1\nsecure=0\nshow_headings=1\nshow_name=1\nshow_articles=1\nshow_link=1\nshow_other_cats=1\nshow_cat_description=1\nshow_cat_items=1\nshow_feed_image=1\nshow_feed_description=1\nshow_item_description=1\nfeed_word_count=0\n\n","0","0","0");
INSERT INTO jos_menu VALUES("50","mainmenu","Neuigkeiten","neuigkeiten","index.php?option=com_content&view=category&layout=blog&id=1","component","1","0","20","0","6","0","0000-00-00 00:00:00","0","0","0","0","show_page_title=1\npage_title=The News\nshow_description=0\nshow_description_image=0\nnum_leading_articles=1\nnum_intro_articles=4\nnum_columns=2\nnum_links=4\nshow_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\norderby_pri=\norderby_sec=\nshow_pagination=2\nshow_pagination_results=1\nshow_noauth=0\nlink_titles=0\nshow_intro=1\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=1\nshow_create_date=1\nshow_modify_date=1\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\n\n","0","0","0");
INSERT INTO jos_menu VALUES("51","usermenu","Beitrag einreichen","beitrag-einreichen","index.php?option=com_content&view=article&layout=form","component","1","0","20","0","2","0","0000-00-00 00:00:00","0","0","2","0","","0","0","0");
INSERT INTO jos_menu VALUES("52","usermenu","Weblink einreichen","weblink-einreichen","index.php?option=com_weblinks&view=weblink&layout=form","component","1","0","4","0","3","0","0000-00-00 00:00:00","0","0","2","0","","0","0","0");
INSERT INTO jos_menu VALUES("53","mainmenu","Girokonten","girokonten","index.php?option=com_content&view=article&id=46","component","1","0","20","0","7","0","0000-00-00 00:00:00","0","0","0","0","show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n","0","0","0");
INSERT INTO jos_menu VALUES("55","mainmenu","Bankenverbünde","bankenverbuende","index.php?option=com_content&view=article&id=49","component","1","0","20","0","8","0","0000-00-00 00:00:00","0","0","0","0","show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=Die deutschen Bankenverbünde\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n","0","0","0");
INSERT INTO jos_menu VALUES("54","mainmenu","Impressum","impressum","index.php?option=com_impressum&view=impressum","component","1","0","34","0","11","62","2009-05-07 15:57:02","0","0","0","0","page_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n","0","0","0");



DROP TABLE IF EXISTS jos_menu_types ;

CREATE TABLE `jos_menu_types` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `menutype` varchar(75) NOT NULL DEFAULT '',
  `title` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `menutype` (`menutype`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO jos_menu_types VALUES("1","mainmenu","Hauptmenü","Das Hauptmenü dieser Website");
INSERT INTO jos_menu_types VALUES("2","usermenu","Benutzermenü","Ein Menü für angemeldete Benutzer");
INSERT INTO jos_menu_types VALUES("3","topmenu","Menü oben","Menü oben");
INSERT INTO jos_menu_types VALUES("6","keyconcepts","Schlüsselkonzepte","Beschreibung einiger kritischer Informationen für neue Benutzer.");



DROP TABLE IF EXISTS jos_messages ;

CREATE TABLE `jos_messages` (
  `message_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id_from` int(10) unsigned NOT NULL DEFAULT '0',
  `user_id_to` int(10) unsigned NOT NULL DEFAULT '0',
  `folder_id` int(10) unsigned NOT NULL DEFAULT '0',
  `date_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `state` int(11) NOT NULL DEFAULT '0',
  `priority` int(1) unsigned NOT NULL DEFAULT '0',
  `subject` text NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `useridto_state` (`user_id_to`,`state`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_messages_cfg ;

CREATE TABLE `jos_messages_cfg` (
  `user_id` int(10) unsigned NOT NULL DEFAULT '0',
  `cfg_name` varchar(100) NOT NULL DEFAULT '',
  `cfg_value` varchar(255) NOT NULL DEFAULT '',
  UNIQUE KEY `idx_user_var_name` (`user_id`,`cfg_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_migration_backlinks ;

CREATE TABLE `jos_migration_backlinks` (
  `itemid` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` text NOT NULL,
  `sefurl` text NOT NULL,
  `newurl` text NOT NULL,
  PRIMARY KEY (`itemid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_modules ;

CREATE TABLE `jos_modules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `ordering` int(11) NOT NULL DEFAULT '0',
  `position` varchar(50) DEFAULT NULL,
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `module` varchar(50) DEFAULT NULL,
  `numnews` int(11) NOT NULL DEFAULT '0',
  `access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `showtitle` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `params` text NOT NULL,
  `iscore` tinyint(4) NOT NULL DEFAULT '0',
  `client_id` tinyint(4) NOT NULL DEFAULT '0',
  `control` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `published` (`published`,`access`),
  KEY `newsfeeds` (`module`,`published`)
) ENGINE=MyISAM AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

INSERT INTO jos_modules VALUES("1","Hauptmenü","","0","left","0","0000-00-00 00:00:00","1","mod_mainmenu","0","0","1","menutype=mainmenu\nmoduleclass_sfx=_menu\n","1","0","");
INSERT INTO jos_modules VALUES("2","Anmeldung","","1","login","0","0000-00-00 00:00:00","1","mod_login","0","0","1","","1","1","");
INSERT INTO jos_modules VALUES("3","Beliebt","","4","cpanel","0","0000-00-00 00:00:00","1","mod_popular","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("4","Neue Beiträge","","5","cpanel","0","0000-00-00 00:00:00","1","mod_latest","0","2","1","ordering=c_dsc\nuser_id=0\ncache=0\n\n","0","1","");
INSERT INTO jos_modules VALUES("5","Statistiken","","6","cpanel","0","0000-00-00 00:00:00","1","mod_stats","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("6","Ungelesene Nachrichten","","1","header","0","0000-00-00 00:00:00","1","mod_unread","0","2","1","","1","1","");
INSERT INTO jos_modules VALUES("7","Benutzer online","","2","header","0","0000-00-00 00:00:00","1","mod_online","0","2","1","","1","1","");
INSERT INTO jos_modules VALUES("8","Toolbar","","1","toolbar","0","0000-00-00 00:00:00","1","mod_toolbar","0","2","1","","1","1","");
INSERT INTO jos_modules VALUES("9","Quick-Icons","","1","icon","0","0000-00-00 00:00:00","1","mod_quickicon","0","2","1","","1","1","");
INSERT INTO jos_modules VALUES("10","Angemeldete Benutzer","","3","cpanel","0","0000-00-00 00:00:00","1","mod_logged","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("11","Fußzeile","","0","footer","0","0000-00-00 00:00:00","1","mod_footer","0","0","1","","1","1","");
INSERT INTO jos_modules VALUES("12","Admin-Menü","","1","menu","0","0000-00-00 00:00:00","1","mod_menu","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("13","Admin-Untermenü","","1","submenu","0","0000-00-00 00:00:00","1","mod_submenu","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("14","Benutzerstatus","","1","status","0","0000-00-00 00:00:00","1","mod_status","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("15","Titel","","1","title","0","0000-00-00 00:00:00","1","mod_title","0","2","1","","0","1","");
INSERT INTO jos_modules VALUES("43","Update nötig?","","2","cpanel","0","0000-00-00 00:00:00","1","mod_jgerman","0","2","1","auto_check=auto_check_no\ncheck_core=check_core_show\nnotice=notice_show\nimagesize=middle\n\n","0","1","");
INSERT INTO jos_modules VALUES("16","Umfragen","","1","left","0","0000-00-00 00:00:00","1","mod_poll","0","0","1","id=15\nmoduleclass_sfx=\ncache=1\ncache_time=900\n\n","0","0","");
INSERT INTO jos_modules VALUES("17","Benutzermenü","","4","left","0","0000-00-00 00:00:00","0","mod_mainmenu","0","1","1","menutype=usermenu\nmoduleclass_sfx=_menu\ncache=1","1","0","");
INSERT INTO jos_modules VALUES("18","Anmeldung","","8","left","0","0000-00-00 00:00:00","0","mod_login","0","0","1","greeting=1\nname=0","1","0","");
INSERT INTO jos_modules VALUES("19","Neueste Nachrichten","","4","user1","0","0000-00-00 00:00:00","1","mod_latestnews","0","0","1","cache=1","1","0","");
INSERT INTO jos_modules VALUES("20","Statistiken","","6","left","0","0000-00-00 00:00:00","0","mod_stats","0","0","1","serverinfo=1\nsiteinfo=1\ncounter=1\nincrease=0\nmoduleclass_sfx=","0","0","");
INSERT INTO jos_modules VALUES("21","Wer ist online","","1","right","0","0000-00-00 00:00:00","0","mod_whosonline","0","0","1","online=1\nusers=1\nmoduleclass_sfx=","0","0","");
INSERT INTO jos_modules VALUES("22","Meist gelesen","","6","user2","0","0000-00-00 00:00:00","0","mod_mostread","0","0","1","cache=1","0","0","");
INSERT INTO jos_modules VALUES("23","Archiv","","9","left","0","0000-00-00 00:00:00","0","mod_archive","0","0","1","cache=1","1","0","");
INSERT INTO jos_modules VALUES("24","Bereiche","","10","left","0","0000-00-00 00:00:00","0","mod_sections","0","0","1","cache=1","1","0","");
INSERT INTO jos_modules VALUES("25","Schlagzeilen","","1","top","0","0000-00-00 00:00:00","0","mod_newsflash","0","0","1","catid=3\nstyle=random\nitems=\nmoduleclass_sfx=","0","0","");
INSERT INTO jos_modules VALUES("26","Verwandte Beiträge","","11","left","0","0000-00-00 00:00:00","0","mod_related_items","0","0","1","","0","0","");
INSERT INTO jos_modules VALUES("27","Suche","","1","user4","0","0000-00-00 00:00:00","1","mod_search","0","0","0","cache=1","0","0","");
INSERT INTO jos_modules VALUES("28","Zufallsbild","","9","right","0","0000-00-00 00:00:00","0","mod_random_image","0","0","1","","0","0","");
INSERT INTO jos_modules VALUES("29","Menü oben","","1","user3","0","0000-00-00 00:00:00","0","mod_mainmenu","0","0","0","cache=1\nmenutype=topmenu\nmenu_style=list_flat\nmenu_images=n\nmenu_images_align=left\nexpand_menu=n\nclass_sfx=-nav\nmoduleclass_sfx=\nindent_image1=0\nindent_image2=0\nindent_image3=0\nindent_image4=0\nindent_image5=0\nindent_image6=0","1","0","");
INSERT INTO jos_modules VALUES("30","Banner","","1","footer","0","0000-00-00 00:00:00","0","mod_banners","0","0","0","target=1\ncount=1\ncid=1\ncatid=33\ntag_search=0\nordering=random\nheader_text=\nfooter_text=\nmoduleclass_sfx=\ncache=1\ncache_time=900\n\n","1","0","");
INSERT INTO jos_modules VALUES("32","Wrapper","","12","left","0","0000-00-00 00:00:00","0","mod_wrapper","0","0","1","","0","0","");
INSERT INTO jos_modules VALUES("33","Fußzeile","","2","footer","0","0000-00-00 00:00:00","0","mod_footer","0","0","0","cache=1\n\n","1","0","");
INSERT INTO jos_modules VALUES("34","Feed-Anzeige","","13","left","0","0000-00-00 00:00:00","1","mod_feed","0","0","1","","1","0","");
INSERT INTO jos_modules VALUES("35","Navigationspfad (Breadcrumb)","","1","breadcrumb","0","0000-00-00 00:00:00","0","mod_breadcrumbs","0","0","1","moduleclass_sfx=\ncache=0\nshowHome=1\nhomeText=Start\nshowComponent=1\nseparator=\n\n","1","0","");
INSERT INTO jos_modules VALUES("36","Syndication","","3","syndicate","0","0000-00-00 00:00:00","1","mod_syndicate","0","0","0","","1","0","");
INSERT INTO jos_modules VALUES("38","Werbung","","3","right","0","0000-00-00 00:00:00","0","mod_banners","0","0","1","count=4\nrandomise=0\ncid=0\ncatid=14\nheader_text=Empfohlene Links:\nfooter_text=<a href=\"http://www.joomla.org\">Inserate von Joomla!</a>\nmoduleclass_sfx=_text\ncache=0\n\n","0","0","");
INSERT INTO jos_modules VALUES("40","Schlüsselkonzepte","","3","left","0","0000-00-00 00:00:00","0","mod_mainmenu","0","0","1","cache=1\nclass_sfx=\nmoduleclass_sfx=_menu\nmenutype=keyconcepts\nmenu_style=list\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nfull_active_id=0\nmenu_images=0\nmenu_images_align=0\nexpand_menu=0\nactivate_parent=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\nwindow_open=\n\n","0","0","");
INSERT INTO jos_modules VALUES("41","Willkommen bei Joomla!","<div style=\"padding: 5px\"><p>Gratulation, dass Sie Joomla! als Ihr Content-Management-System gewählt haben. Wir hoffen, dass es Ihnen mit unserem Programm gelingt eine erfolgreiche Website zu erstellen und der Gemeinschaft vielleicht zu einem späteren Punkt etwas zurückgeben können.</p><p>Um Ihren Anfang mit Joomla! so einfach wie möglich zu gestalten, möchten wir Ihnen einige Punkte aufzeigen, wie z.B. allgemeinen Fragen, Hilfen und Sicherheit Ihres Server.</p><p>Sie sollten mit dem &quot;<a href=\"http://docs.joomla.org/beginners\" target=\"_blank\">Absolute Beginners Guide to Joomla!</a>&quot; anfangen und dann, um die Sicherheit Ihres Servers zu gewährleisten, die &quot;<a href=\"http://forum.joomla.org/viewtopic.php?t=81058\" target=\"_blank\">Security Checklist</a>&quot; lesen.</p><p>Für Ihre häufig gestellten Fragen sollten Sie zuerst ins <a href=\"http://forum.joomla.org\" target=\"_blank\">Forum</a> schauen und die <a href=\"http://docs.joomla.org/Category:FAQ\" target=\"_blank\">FAQ</a> im Wiki lesen. Im Forum finden Sie eine Antwort auf fast alle Ihre Fragen. Auch wenn diese bisher nur einmal von anderen beantwortet wurden, so ist das Forum ein großes Nachschlagewerk für Anfänger und Profis. Bitte benutzen Sie die Suchfunktion des Forums bevor Sie Ihre Frage stellen, es könnte nämlich sein, dass diese schon einmal gestellt wurde. <img alt=\"Lächeln\" border=\"0\" src=\"../plugins/editors/tinymce/jscripts/tiny_mce/plugins/emotions/images/smiley-smile.gif\" title=\"Lächeln\" /></p><p>Die Sicherheit ist ein großes Anliegen für uns, deshalb würden wir es begrüßen, wenn Sie das &quot;<a href=\"http://forum.joomla.org/viewforum.php?f=8\" target=\"_blank\">Announcement-Forum</a>&quot; abonnieren würden, damit Sie immer aktuelle Informationen über neue Joomla!-Versionen bekommen. Sie sollten aber auch regelmäßig das &quot;<a href=\"http://forum.joomla.org/viewforum.php?f=432\" target=\"_blank\">Security-Forum</a>&quot; besuchen.</p><p>Wir hoffen, dass Sie viel Spaß und Erfolg mit Joomla! haben und Sie bald unter den Hunderten bzw. Tausenden an Joomla!-Benutzern sind, die Anfängern helfen können.</p><p>Ihr Joomla!-Team</p><p>P.S.: Um diese Anzeige zu entfernen, löschen Sie einfach das &quot;Willkommen bei Joomla!&quot;-Modul unter &quot;Erweiterungen&quot;-&gt;&quot;Module&quot; -&gt;&quot;Administrator&quot;.</p></div>","1","cpanel","0","0000-00-00 00:00:00","1","mod_custom","0","2","1","moduleclass_sfx=\n\n","1","1","");
INSERT INTO jos_modules VALUES("42","Joomla! Security Newsfeed","","6","cpanel","62","2008-10-25 20:15:17","1","mod_feed","0","0","1","cache=1\ncache_time=15\nmoduleclass_sfx=\nrssurl=http://feeds.joomla.org/JoomlaSecurityNews\nrssrtl=0\nrsstitle=1\nrssdesc=0\nrssimage=1\nrssitems=1\nrssitemdesc=1\nword_count=0\n\n","0","1","");



DROP TABLE IF EXISTS jos_modules_menu ;

CREATE TABLE `jos_modules_menu` (
  `moduleid` int(11) NOT NULL DEFAULT '0',
  `menuid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`moduleid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO jos_modules_menu VALUES("1","0");
INSERT INTO jos_modules_menu VALUES("16","1");
INSERT INTO jos_modules_menu VALUES("17","0");
INSERT INTO jos_modules_menu VALUES("18","1");
INSERT INTO jos_modules_menu VALUES("19","1");
INSERT INTO jos_modules_menu VALUES("19","2");
INSERT INTO jos_modules_menu VALUES("19","4");
INSERT INTO jos_modules_menu VALUES("19","27");
INSERT INTO jos_modules_menu VALUES("19","36");
INSERT INTO jos_modules_menu VALUES("21","1");
INSERT INTO jos_modules_menu VALUES("22","1");
INSERT INTO jos_modules_menu VALUES("22","2");
INSERT INTO jos_modules_menu VALUES("22","4");
INSERT INTO jos_modules_menu VALUES("22","27");
INSERT INTO jos_modules_menu VALUES("22","36");
INSERT INTO jos_modules_menu VALUES("25","0");
INSERT INTO jos_modules_menu VALUES("27","0");
INSERT INTO jos_modules_menu VALUES("29","0");
INSERT INTO jos_modules_menu VALUES("30","0");
INSERT INTO jos_modules_menu VALUES("31","1");
INSERT INTO jos_modules_menu VALUES("32","0");
INSERT INTO jos_modules_menu VALUES("33","0");
INSERT INTO jos_modules_menu VALUES("34","0");
INSERT INTO jos_modules_menu VALUES("35","0");
INSERT INTO jos_modules_menu VALUES("36","0");
INSERT INTO jos_modules_menu VALUES("38","1");
INSERT INTO jos_modules_menu VALUES("39","43");
INSERT INTO jos_modules_menu VALUES("39","44");
INSERT INTO jos_modules_menu VALUES("39","45");
INSERT INTO jos_modules_menu VALUES("39","46");
INSERT INTO jos_modules_menu VALUES("39","47");
INSERT INTO jos_modules_menu VALUES("40","0");



DROP TABLE IF EXISTS jos_newsfeeds ;

CREATE TABLE `jos_newsfeeds` (
  `catid` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `alias` varchar(255) NOT NULL DEFAULT '',
  `link` text NOT NULL,
  `filename` varchar(200) DEFAULT NULL,
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `numarticles` int(11) unsigned NOT NULL DEFAULT '1',
  `cache_time` int(11) unsigned NOT NULL DEFAULT '3600',
  `checked_out` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `rtl` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `published` (`published`),
  KEY `catid` (`catid`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

INSERT INTO jos_newsfeeds VALUES("4","1","Joomla! Ankündigungen","joomla-ankuendigungen","http://feeds.joomla.org/JoomlaAnnouncements","","0","5","3600","0","0000-00-00 00:00:00","1","0");
INSERT INTO jos_newsfeeds VALUES("4","2","Joomla! Core-Team Blog","joomla-core-team-blog","http://feeds.joomla.org/JoomlaCommunityCoreTeamBlog","","0","5","3600","0","0000-00-00 00:00:00","2","0");
INSERT INTO jos_newsfeeds VALUES("4","3","Joomla! Community-Magazin","joomla-community-magazin","http://feeds.joomla.org/JoomlaMagazine","","0","20","3600","0","0000-00-00 00:00:00","3","0");
INSERT INTO jos_newsfeeds VALUES("4","4","Joomla! Developer-News","joomla-developer-news","http://feeds.joomla.org/JoomlaDeveloper","","0","5","3600","0","0000-00-00 00:00:00","4","0");
INSERT INTO jos_newsfeeds VALUES("4","5","Joomla! Sicherheits-News","joomla-sicherheits-news","http://feeds.joomla.org/JoomlaSecurityNews","","0","5","3600","0","0000-00-00 00:00:00","5","0");
INSERT INTO jos_newsfeeds VALUES("5","6","Free Software Foundation Blogs","free-software-foundation-blogs","http://www.fsf.org/blogs/RSS","","0","5","3600","0","0000-00-00 00:00:00","4","0");
INSERT INTO jos_newsfeeds VALUES("5","7","Free Software Foundation","free-software-foundation","http://www.fsf.org/news/RSS","","0","5","3600","0","0000-00-00 00:00:00","3","0");
INSERT INTO jos_newsfeeds VALUES("5","8","Software Freedom Law Center Blog","software-freedom-law-center-blog","http://www.softwarefreedom.org/feeds/blog/","","0","5","3600","0","0000-00-00 00:00:00","2","0");
INSERT INTO jos_newsfeeds VALUES("5","9","Software Freedom Law Center News","software-freedom-law-center","http://www.softwarefreedom.org/feeds/news/","","0","5","3600","0","0000-00-00 00:00:00","1","0");
INSERT INTO jos_newsfeeds VALUES("5","10","Open Source Initiative Blog","open-source-initiative-blog","http://www.opensource.org/blog/feed","","0","5","3600","0","0000-00-00 00:00:00","5","0");
INSERT INTO jos_newsfeeds VALUES("6","11","PHP-News und Ankündigungen","php-news-und-ankuendigungen","http://www.php.net/feed.atom","","0","5","3600","0","0000-00-00 00:00:00","1","0");
INSERT INTO jos_newsfeeds VALUES("6","12","Planet MySQL","planet-mysql","http://www.planetmysql.org/rss20.xml","","0","5","3600","0","0000-00-00 00:00:00","2","0");
INSERT INTO jos_newsfeeds VALUES("6","13","Linux Foundation Ankündigungen","linux-foundation-ankuendigungen","http://www.linuxfoundation.org/press/rss20.xml","","0","5","3600","0","0000-00-00 00:00:00","3","0");
INSERT INTO jos_newsfeeds VALUES("6","14","Mootools Blog","mootools-blog","http://feeds.feedburner.com/mootools-blog","","0","5","3600","0","0000-00-00 00:00:00","4","0");
INSERT INTO jos_newsfeeds VALUES("4","15","J!German News","jgerman-news","http://www.jgerman.de/feed/rss.html","","0","5","3600","0","0000-00-00 00:00:00","6","0");



DROP TABLE IF EXISTS jos_plugins ;

CREATE TABLE `jos_plugins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `element` varchar(100) NOT NULL DEFAULT '',
  `folder` varchar(100) NOT NULL DEFAULT '',
  `access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `published` tinyint(3) NOT NULL DEFAULT '0',
  `iscore` tinyint(3) NOT NULL DEFAULT '0',
  `client_id` tinyint(3) NOT NULL DEFAULT '0',
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `params` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_folder` (`published`,`client_id`,`access`,`folder`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

INSERT INTO jos_plugins VALUES("1","Authentifikation - Joomla","joomla","authentication","0","1","1","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("2","Authentifikation - LDAP","ldap","authentication","0","2","0","1","0","0","0000-00-00 00:00:00","host=\nport=389\nuse_ldapV3=0\nnegotiate_tls=0\nno_referrals=0\nauth_method=bind\nbase_dn=\nsearch_string=\nusers_dn=\nusername=\npassword=\nldap_fullname=fullName\nldap_email=mail\nldap_uid=uid\n\n");
INSERT INTO jos_plugins VALUES("3","Authentifikation - GMail","gmail","authentication","0","4","0","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("4","Authentifikation - OpenID","openid","authentication","0","3","0","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("5","Benutzer - Joomla!","joomla","user","0","0","1","0","0","0","0000-00-00 00:00:00","autoregister=1\n\n");
INSERT INTO jos_plugins VALUES("6","Suche - Inhalte","content","search","0","1","1","1","0","0","0000-00-00 00:00:00","search_limit=50\nsearch_content=1\nsearch_uncategorised=1\nsearch_archived=1\n\n");
INSERT INTO jos_plugins VALUES("7","Suche - Kontakte","contacts","search","0","3","1","1","0","0","0000-00-00 00:00:00","search_limit=50\n\n");
INSERT INTO jos_plugins VALUES("8","Suche - Kategorien","categories","search","0","4","1","0","0","0","0000-00-00 00:00:00","search_limit=50\n\n");
INSERT INTO jos_plugins VALUES("9","Suche - Bereiche","sections","search","0","5","1","0","0","0","0000-00-00 00:00:00","search_limit=50\n\n");
INSERT INTO jos_plugins VALUES("10","Suche - Newsfeeds","newsfeeds","search","0","6","1","0","0","0","0000-00-00 00:00:00","search_limit=50\n\n");
INSERT INTO jos_plugins VALUES("11","Suche - Weblinks","weblinks","search","0","2","1","1","0","0","0000-00-00 00:00:00","search_limit=50\n\n");
INSERT INTO jos_plugins VALUES("12","Inhalt - Seitenumbruch","pagebreak","content","0","10000","1","1","0","0","0000-00-00 00:00:00","enabled=1\ntitle=1\nmultipage_toc=1\nshowall=1\n\n");
INSERT INTO jos_plugins VALUES("13","Inhalt - Bewertung","vote","content","0","4","1","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("14","Inhalt - E-Mail-Verschleierung","emailcloak","content","0","5","1","0","0","0","0000-00-00 00:00:00","mode=1\n\n");
INSERT INTO jos_plugins VALUES("15","Inhalt - Code-Hervorhebung (GeSHi)","geshi","content","0","5","0","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("16","Inhalt - Modul laden","loadmodule","content","0","6","1","0","0","0","0000-00-00 00:00:00","enabled=1\nstyle=0\n\n");
INSERT INTO jos_plugins VALUES("17","Inhalt - Seitennavigation","pagenavigation","content","0","2","1","1","0","0","0000-00-00 00:00:00","position=1\n\n");
INSERT INTO jos_plugins VALUES("18","Editor - Kein Editor","none","editors","0","0","1","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("19","Editor - TinyMCE 2.0","tinymce","editors","0","0","1","1","0","0","0000-00-00 00:00:00","theme=advanced\ncleanup=1\ncleanup_startup=0\nautosave=0\ncompressed=0\nrelative_urls=1\ntext_direction=ltr\nlang_mode=0\nlang_code=de\ninvalid_elements=applet\ncontent_css=1\ncontent_css_custom=\nnewlines=0\ntoolbar=top\nhr=1\nsmilies=1\ntable=1\nstyle=1\nlayer=1\nxhtmlxtras=0\ntemplate=0\ndirectionality=1\nfullscreen=1\nhtml_height=550\nhtml_width=750\npreview=1\ninsertdate=1\nformat_date=%Y-%m-%d\ninserttime=1\nformat_time=%H:%M:%S\n\n");
INSERT INTO jos_plugins VALUES("20","Editor - XStandard Lite 2.0","xstandard","editors","0","0","0","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("21","Editorbutton - Bild","image","editors-xtd","0","0","1","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("22","Editorbutton - Seitenumbruch","pagebreak","editors-xtd","0","0","1","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("23","Editorbutton - Weiterlesen","readmore","editors-xtd","0","0","1","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("24","XML-RPC - Joomla","joomla","xmlrpc","0","7","0","1","0","62","2009-05-07 15:28:52","");
INSERT INTO jos_plugins VALUES("25","XML-RPC - Blogger-API","blogger","xmlrpc","0","7","0","1","0","0","0000-00-00 00:00:00","catid=1\nsectionid=0\n\n");
INSERT INTO jos_plugins VALUES("27","System - SEF","sef","system","0","1","1","0","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("28","System - Debug","debug","system","0","2","1","0","0","0","0000-00-00 00:00:00","queries=1\nmemory=1\nlangauge=1\n\n");
INSERT INTO jos_plugins VALUES("29","System - Legacy","legacy","system","0","3","0","1","0","62","2009-05-07 15:14:42","route=0\n\n");
INSERT INTO jos_plugins VALUES("30","System - Cache","cache","system","0","4","0","1","0","0","0000-00-00 00:00:00","browsercache=0\ncachetime=15\n\n");
INSERT INTO jos_plugins VALUES("31","System - Protokoll","log","system","0","5","0","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("32","System - Remember Me","remember","system","0","6","1","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("33","System - Backlink","backlink","system","0","7","0","1","0","0","0000-00-00 00:00:00","");
INSERT INTO jos_plugins VALUES("34","RD Add PHP","rd_addphp","content","0","0","1","0","0","0","0000-00-00 00:00:00","");



DROP TABLE IF EXISTS jos_poll_data ;

CREATE TABLE `jos_poll_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pollid` int(11) NOT NULL DEFAULT '0',
  `text` text NOT NULL,
  `hits` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `pollid` (`pollid`,`text`(1))
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

INSERT INTO jos_poll_data VALUES("1","14","Community-Seiten","2");
INSERT INTO jos_poll_data VALUES("2","14","Öffentliche Firmenseiten","3");
INSERT INTO jos_poll_data VALUES("3","14","eCommerce","1");
INSERT INTO jos_poll_data VALUES("4","14","Blogs","0");
INSERT INTO jos_poll_data VALUES("5","14","Intranets","0");
INSERT INTO jos_poll_data VALUES("6","14","Foto- und Medienseiten","2");
INSERT INTO jos_poll_data VALUES("7","14","Alles oben genannte!","3");
INSERT INTO jos_poll_data VALUES("8","14","","0");
INSERT INTO jos_poll_data VALUES("9","14","","0");
INSERT INTO jos_poll_data VALUES("10","14","","0");
INSERT INTO jos_poll_data VALUES("11","14","","0");
INSERT INTO jos_poll_data VALUES("12","14","","0");
INSERT INTO jos_poll_data VALUES("13","15","kostenlose Kontoführung","2");
INSERT INTO jos_poll_data VALUES("14","15","Kreditkarte(n)","0");
INSERT INTO jos_poll_data VALUES("15","15","Anzahl Geldautomaten","2");
INSERT INTO jos_poll_data VALUES("16","15","Kundenberatung","1");
INSERT INTO jos_poll_data VALUES("17","15","Guthabenzins","0");
INSERT INTO jos_poll_data VALUES("18","15","niedriger Sollzins","0");
INSERT INTO jos_poll_data VALUES("19","15","Sonstiges","0");
INSERT INTO jos_poll_data VALUES("20","15","","0");
INSERT INTO jos_poll_data VALUES("21","15","","0");
INSERT INTO jos_poll_data VALUES("22","15","","0");
INSERT INTO jos_poll_data VALUES("23","15","","0");
INSERT INTO jos_poll_data VALUES("24","15","","0");



DROP TABLE IF EXISTS jos_poll_date ;

CREATE TABLE `jos_poll_date` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `vote_id` int(11) NOT NULL DEFAULT '0',
  `poll_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `poll_id` (`poll_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

INSERT INTO jos_poll_date VALUES("1","2007-10-09 13:01:58","1","14");
INSERT INTO jos_poll_date VALUES("2","2007-10-10 15:19:43","7","14");
INSERT INTO jos_poll_date VALUES("3","2007-10-11 11:08:16","7","14");
INSERT INTO jos_poll_date VALUES("4","2007-10-11 15:02:26","2","14");
INSERT INTO jos_poll_date VALUES("5","2007-10-11 15:43:03","7","14");
INSERT INTO jos_poll_date VALUES("6","2007-10-11 15:43:38","7","14");
INSERT INTO jos_poll_date VALUES("7","2007-10-12 00:51:13","2","14");
INSERT INTO jos_poll_date VALUES("8","2008-05-10 19:12:29","3","14");
INSERT INTO jos_poll_date VALUES("9","2008-05-14 14:18:00","6","14");
INSERT INTO jos_poll_date VALUES("10","2008-06-10 15:20:29","6","14");
INSERT INTO jos_poll_date VALUES("11","2008-07-03 12:37:53","2","14");
INSERT INTO jos_poll_date VALUES("12","2009-05-05 20:14:44","13","15");
INSERT INTO jos_poll_date VALUES("13","2009-05-07 14:56:22","15","15");
INSERT INTO jos_poll_date VALUES("14","2009-05-07 15:59:29","13","15");
INSERT INTO jos_poll_date VALUES("15","2009-05-11 15:47:01","15","15");
INSERT INTO jos_poll_date VALUES("16","2011-02-18 14:42:00","16","15");



DROP TABLE IF EXISTS jos_poll_menu ;

CREATE TABLE `jos_poll_menu` (
  `pollid` int(11) NOT NULL DEFAULT '0',
  `menuid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pollid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_polls ;

CREATE TABLE `jos_polls` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `voters` int(9) NOT NULL DEFAULT '0',
  `checked_out` int(11) NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `access` int(11) NOT NULL DEFAULT '0',
  `lag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

INSERT INTO jos_polls VALUES("14","Wozu nutzen Sie Joomla!?","wozu-nutzen-sie-joomla","11","0","0000-00-00 00:00:00","0","0","86400");
INSERT INTO jos_polls VALUES("15","Was ist ihnen bei einem Girokonto besonders wichtig?","girokonto-wichtig","5","62","2009-05-05 20:09:53","1","0","86400");



DROP TABLE IF EXISTS jos_sections ;

CREATE TABLE `jos_sections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `image` text NOT NULL,
  `scope` varchar(50) NOT NULL DEFAULT '',
  `image_position` varchar(30) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `checked_out` int(11) unsigned NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `count` int(11) NOT NULL DEFAULT '0',
  `params` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_scope` (`scope`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO jos_sections VALUES("1","News","","news","articles.jpg","content","right","Wählen Sie eines der unten angeführten News-Themen, dann den Beitrag zum Lesen.","1","62","2009-05-05 16:38:02","3","0","2","");
INSERT INTO jos_sections VALUES("3","FAQs","","faqs","key.jpg","content","left","Wählen Sie eines der unten angeführten FAQ-Themen, dann eine FAQ. Sollten Sie eine Frage haben welche hier nicht beantwortet ist, kontaktieren Sie uns bitte.","0","0","0000-00-00 00:00:00","5","0","23","");
INSERT INTO jos_sections VALUES("4","Über Joomla!","","ueber-joomla","","content","left","","0","0","0000-00-00 00:00:00","2","0","14","");
INSERT INTO jos_sections VALUES("5","Finanzen","","finanzen","","content","left","Vergleich verschiedener Finanzdienstleistungen","1","0","0000-00-00 00:00:00","6","0","2","");



DROP TABLE IF EXISTS jos_session ;

CREATE TABLE `jos_session` (
  `username` varchar(150) DEFAULT '',
  `time` varchar(14) DEFAULT '',
  `session_id` varchar(200) NOT NULL DEFAULT '0',
  `guest` tinyint(4) DEFAULT '1',
  `userid` int(11) DEFAULT '0',
  `usertype` varchar(50) DEFAULT '',
  `gid` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `client_id` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `data` longtext,
  PRIMARY KEY (`session_id`(64)),
  KEY `whosonline` (`guest`,`usertype`),
  KEY `userid` (`userid`),
  KEY `time` (`time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO jos_session VALUES("","1325775474","29ca60c9cbf95c88302f025fd827f4f9","1","0","","0","0","i55Yu43oksxDzP_USIkKYEVS892WiYbfPXSYunbDHQk7rWlKEhUDlQcpVrA_mNaH4YvbN67GB7Gti0OxTFMiN6ZVDFh7_DOLAg1ZSy37Jw9sBmBeboHs448R7bWUQWYdBIfHps6nGHxZDIYr5AlZMe9SI8DZ-cE_87bPyltKz_d81H5I4U3Z-dQRk_KizkXJ99kcnvDOBX89gVHxqqyP5bGo3D6k1N7qZXRM08EAdfH78sSH5aqT2mYK0n5AB8EoXuukXbtbVErjDD-U9ImZ-_EXPkejIIMkzMxydN72hGzdkcfnk58zQ_YJej15YIOfnNL6fl7tHVtewkIkyKL7mi5SleUeyf2bLlxwXEJPl1Wl7HYvqmne1bXIF6ujC810f8cMEQfm2_Y0fqZaZcvukt7WvANTXu_BLvMOGTxnes2_syhfp0hLPcyH8Xs1wdu9ulQpgcQn9vAJBKB34ydkt2j0iW-S3vqQqOJg79dZ2JtQw9-xXS5Znk3sIvbPD2TFeOuT3FGXPwyMULjUMnYVfIq3sfKtJxXMxZCicyvnozFqOFAby1oMxYlwuEWbuJcP7gfRnRL9kOCjLDnfleIxGLP97EtZZUAwVPmVyQUPWP_O1-EBPKL6L_u5ul6BIhWiDzmxEtvfQa8Wsl-WNtH0BWSTq58yUt9iJfqZd4numhFQE8zW-4bTE2eKENJ33Dg4xx5IRNGBFWtKqiBqnppySFUvvLfREUr_kphwiGiT18u55gh8XAIbRNmK0GasWE3Mh2tetYpHUiif9GgHjw5DD4K_dbkdfPIFIUpqJGqbZZhQDKkzZSKsx4RdaekGZMBqxshsJT10tiA-OoAIcCYHyCw42xwKVOmgoTemwWfKxsODKEyMA2Jx7gipFdomoPPJIVeX8m02tzIXrISgUo3niVkP786q8oMjC1ExSpAzfHcJG38nsrypzrqZQaB_UNKuNTKPxLRJr-NQ8RW2IQ4uR-k31a6UKzImxK5orKPvV5DDqtGgoLeuD2p0PImfuVH7Uilh9JkVE-Dc8VA0c6YzFmRMuFrMugaGBQMqsm9w12QyaSsQlPr3i6omkpm7fXt2NHDFqWDcDBgMFgj5N_I8JGiPs8BdG6QAR06GYjQgHayO0PTd9_Wj_mXnbQgzr8rMGnNGQZoSHmUKtMd7QkwqWPzKRXj0eFVfTh4x_eBanXa5BdzWgp7bKia45H4YEZmBQ7zuygPlvpbtCZLcy33K8NgpuItcmPGH1yqcrfDtIbQ8YcIRtoJXGr9u2scMsY0F-8MiMGhiraDE-9k5dSPC49HQjroZiaOEsghNFGoVXatEeeUHtM1fwtUknDLPdcxEkyHvxRH1C1kl-u1BbVDE7gRr_oZAVHMIZcq2Tao6AT7AlTPzfRARA7PS1gm0GHcpJhaIAd-f_IniuzQAJPoVygA5zMTyYhzKnTuDOAROXIM8yA1nBloz0LQwVbYyGQ3azsBJ_f0FvlZRQYgyYNA0-pLDNPzXRermYNaNwzr1ZSUbtVwBY7NAy1HqLUBY4W-ipi11a5Mk9BIjXZFLWxrxXtc7XnwTQgepWsSWpyBJsxA.");
INSERT INTO jos_session VALUES("","1325775762","31affe00eaa2caa7f00bf21fca7bdd2b","1","0","","0","0","mUmSNx2SvuAZy51sONy1ZuKuCtYzNDVKbmdh4lwg7_NtPs6QeoHB4kDmoQED1curZHMmiem-zFYjOtQVXLVoxuT4q3izQqVEVwGmQeyKH7Qof-H_zAt09c38tw8yc-bq1F2KeRXrmAHGB-RQ_xnvPNxw_YFZsPqa8_Bxz1teJ-KFcUAq2oFy142KbArN2X_lwibOMliVIxd5bL0jVAouMEMsKKa4rfXEZJ77L843oPQFvkd6jb31wzyM6Eg94jr4sW85Ec6AnsCUinzYOBPKxXRyuYDLFGDZE5MyEYdJ-_faxlE6pCEFsIdC0jfqwIL8Mq7Hsr-AXMaWcpE9PLKIpJXBTuHkm9Sqj2E1b8FXwTVDJjZhLYCEeTHi9_N7lCOGQbW1kTB1s14i25H-sJU8EB2HxmHd5jf2eTp_uqdgo1M7Y0OaPgloJ7CngYnrcP67hZQGfbaGsiK3g6yYTlknGAx8JUy8oM0vCFhMw6yzDIReND16x2L85Oq7aQ9d0AYg-t7Nd5ST4sQfT--f9tUSejkkR42SAVGWRupvB2p5tY9qNJEIH5NF4IKsKczZhiaTbpHn1dT9f7CjmS0LLEQsF_1lLoD8gCfrAu0hbBKiTnL2-hH95ev1q-PXML5ZCn65GGLQ5XIeTmgIUAkXN88aFYrX64LHeB5gew4Xu1-VbY96MRHoIL4Cqc3jUrG102PTdjBfrN7p9nxGMcyrIfcbk0QVZIxrge9FQhsfvsNbnCLfpoFrkkt8viEb2JrclTGXEZYINZ9tA1Sw4sSW0JN04w16WOs9wB8AhqJBfC61wkVZPmAlPp5t2Stt9biRctqQa5dN_bHG5ILsq2AHEzf-ySFCgTzni8Z-uvFD1c9kEJUA7g9R5rGCuppUvzXlJxIf-8QsUuLFwwqLJPNuSh37lEwmoJRUUMDLnT1YE8Ejy0sEvqy5mUBhIKFseqsa4Avtnh3nXSgs6yHmZDOzTs8yRfWT4OgmVn39GmqJbwPrl2badxDeFJ2NLz_C4YXi7sQHbExcfzDXFxvQmv44kc-BfDByskAK9Sd1UxZ9AN8bBej-ZoXqChI5QcudG5wHhqtcxhj1fnHIeM-5gJ0go7hpNb3VzFOINVboIKlzQj4TwfXPlift2T7dMTFVvsH0t8oNP_69_LQyJk3D7e1fp7s2790CvSRPQzs5cLrEckdfok_QF3n3AntzlOeMi4NUEAOGfhPhkrtyV9rw5Yx8xExdxdivgfX9qAFNAC7GHewC0CnjRgGRgJtazFpeovg_73Gmx8KxJfnV85kv8PfXfJKeiKreq65jEQ2GYUytDFRKBiWE2NrVyTfQ1lvRtAsVQfvry1ij00bo2yoMhw_O-9b1TDm-0vgoloD2VVbJ7kIriTDDfAv7R8EMe5krwIbjVwGIZ0wvCWOOcS2Rbm5zHZwlUN3BclI6yXMPBgnoK_gzcaZ0Z-RHSNWjEHrdXQPOK_CxNAXcBNv5Vzu-Hlvs1HYQO4cIjcOlz8JIFBCc3pZ2DXbc1P_HlUexOXgr0LicyWqcP1Hig8X5vYAi73z5Vs61XbbWmPh6QfSnkPUZciXKlGI.");



DROP TABLE IF EXISTS jos_stats_agents ;

CREATE TABLE `jos_stats_agents` (
  `agent` varchar(255) NOT NULL DEFAULT '',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `hits` int(11) unsigned NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS jos_templates_menu ;

CREATE TABLE `jos_templates_menu` (
  `template` varchar(255) NOT NULL DEFAULT '',
  `menuid` int(11) NOT NULL DEFAULT '0',
  `client_id` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`menuid`,`client_id`,`template`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO jos_templates_menu VALUES("siteground-j15-59","0","0");
INSERT INTO jos_templates_menu VALUES("khepri","0","1");



DROP TABLE IF EXISTS jos_users ;

CREATE TABLE `jos_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `username` varchar(150) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  `usertype` varchar(25) NOT NULL DEFAULT '',
  `block` tinyint(4) NOT NULL DEFAULT '0',
  `sendEmail` tinyint(4) DEFAULT '0',
  `gid` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `registerDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastvisitDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `activation` varchar(100) NOT NULL DEFAULT '',
  `params` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usertype` (`usertype`),
  KEY `idx_name` (`name`),
  KEY `gid_block` (`gid`,`block`),
  KEY `username` (`username`),
  KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

INSERT INTO jos_users VALUES("62","Administrator","admin","moi@gratis-toolz.de","10e5a494f12ca80185b8c3039cf6a5e1:orNjP4ApNJbBNDDdu58Drc8Q5JMPDHnr","Super Administrator","0","1","25","2009-05-05 17:05:28","2009-05-12 18:54:16","","");



DROP TABLE IF EXISTS jos_weblinks ;

CREATE TABLE `jos_weblinks` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `catid` int(11) NOT NULL DEFAULT '0',
  `sid` int(11) NOT NULL DEFAULT '0',
  `title` varchar(250) NOT NULL DEFAULT '',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(250) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `hits` int(11) NOT NULL DEFAULT '0',
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `checked_out` int(11) NOT NULL DEFAULT '0',
  `checked_out_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL DEFAULT '0',
  `archived` tinyint(1) NOT NULL DEFAULT '0',
  `approved` tinyint(1) NOT NULL DEFAULT '1',
  `params` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `catid` (`catid`,`published`,`archived`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO jos_weblinks VALUES("1","2","0","Joomla!","joomla","http://www.joomla.org","Das Zuhause von Joomla!","2005-02-14 15:19:02","3","0","0","0000-00-00 00:00:00","1","0","1","target=0");
INSERT INTO jos_weblinks VALUES("2","2","0","php.net","php","http://www.php.net","Die Sprache, mit der Joomla! entwickelt wird","2004-07-07 11:33:24","6","0","0","0000-00-00 00:00:00","3","0","1","");
INSERT INTO jos_weblinks VALUES("3","2","0","MySQL","mysql","http://www.mysql.com","Die Datenbank, die Joomla! nutzt","2004-07-07 10:18:31","1","0","0","0000-00-00 00:00:00","5","0","1","");
INSERT INTO jos_weblinks VALUES("4","2","0","OpenSourceMatters","opensourcematters","http://www.opensourcematters.org","Das Zuhause von OSM","2005-02-14 15:19:02","11","0","0","0000-00-00 00:00:00","2","0","1","target=0");
INSERT INTO jos_weblinks VALUES("5","2","0","Joomla!-Foren","joomla-foren","http://forum.joomla.org","Joomla! Foren","2005-02-14 15:19:02","4","0","0","0000-00-00 00:00:00","4","0","1","target=0");
INSERT INTO jos_weblinks VALUES("6","2","0","Ohloh über Joomla!","ohloh-ueber-joomla","http://www.ohloh.net/projects/20","Sachliche Berichte von Ohloo über die Aktivitäten der Joomla-Entwicklung. Joomla! hat einige ernsthaft anerkannte Star-Programmierer.","2007-07-19 09:28:31","1","0","0","0000-00-00 00:00:00","6","0","1","target=0\n\n");



DROP TABLE IF EXISTS layout_types ;

CREATE TABLE `layout_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `description` varchar(255) NOT NULL,
  `columns` int(11) NOT NULL,
  `weight` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

INSERT INTO layout_types VALUES("1","50:50 Teilung","","0","50:50");
INSERT INTO layout_types VALUES("2","33:66 Teilung","","0","33:66");
INSERT INTO layout_types VALUES("3","25:75 Teilung","","0","25:75");
INSERT INTO layout_types VALUES("4","Golder Schnitt links","","0","38:62");
INSERT INTO layout_types VALUES("5","66:33 Teilung","","0","66:33");
INSERT INTO layout_types VALUES("6","75:25 Teilung","","0","75:25");
INSERT INTO layout_types VALUES("7","Goldener Schnitt rechts","","0","62:38");
INSERT INTO layout_types VALUES("8","3 Spalten selbe Größe","","0","33:33:33");



DROP TABLE IF EXISTS links ;

CREATE TABLE `links` (
  `id` int(11) NOT NULL,
  `text` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `link` text COLLATE latin1_german1_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

INSERT INTO links VALUES("3","www.comdirect.de","http://partners.webmasterplan.com/click.asp?ref=489507&site=3692&type=text&tnb=19");
INSERT INTO links VALUES("5","www.ing-diba.de","http://partners.webmasterplan.com/click.asp?ref=489507&site=5275&type=text&tnb=23");
INSERT INTO links VALUES("4","www.dkb.de","http://ad.zanox.com/ppc/?12212231C2047079682T");
INSERT INTO links VALUES("6","www.volkswagenbank.de","http://ad.zanox.com/ppc/?12230536C1861532616T");
INSERT INTO links VALUES("7","www.wuestenrotdirekt.de","http://www.wuestenrotdirekt.de");
INSERT INTO links VALUES("8","www.netbank.de","http://www.zanox-affiliate.de/ppc/?12213548C545731232T");
INSERT INTO links VALUES("9","www.1822direkt.com","http://ad.zanox.com/ppc/?12235519C334770162T");
INSERT INTO links VALUES("10","www.dab-bank.de","http://ad.zanox.com/ppc/?12213322C711222130T");



DROP TABLE IF EXISTS log_entries ;

CREATE TABLE `log_entries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ipaddress` int(11) NOT NULL,
  `action` tinyint(1) NOT NULL,
  `actionDate` datetime NOT NULL,
  `object_class` varchar(150) NOT NULL,
  `object_id` int(11) NOT NULL,
  `data` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `log_entries_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS menu_entries ;

CREATE TABLE `menu_entries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `page_id` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `published` tinyint(1) NOT NULL,
  `validFrom` datetime NOT NULL,
  `validTo` datetime NOT NULL,
  `order` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `role_id` (`role_id`),
  KEY `page_id` (`page_id`),
  CONSTRAINT `menu_entries_ibfk_5` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`) ON DELETE CASCADE,
  CONSTRAINT `menu_entries_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `menu_entries_ibfk_4` FOREIGN KEY (`parent_id`) REFERENCES `menu_entries` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;

INSERT INTO menu_entries VALUES("63","0","2","22","home","0","0000-00-00 00:00:00","0000-00-00 00:00:00","0");



DROP TABLE IF EXISTS pages ;

CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(150) NOT NULL,
  `name` varchar(150) NOT NULL,
  `validFrom` datetime NOT NULL,
  `validTo` datetime NOT NULL,
  `published` tinyint(1) NOT NULL,
  `metaTags` text NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateLastChange` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `pages_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

INSERT INTO pages VALUES("22","4","home","/","0000-00-00 00:00:00","0000-00-00 00:00:00","0","","0000-00-00 00:00:00","0000-00-00 00:00:00","");



DROP TABLE IF EXISTS permissions ;

CREATE TABLE `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plugin_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `action` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `plugin_id` (`plugin_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `permissions_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `permissions_ibfk_3` FOREIGN KEY (`plugin_id`) REFERENCES `plugins` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS plugin_views ;

CREATE TABLE `plugin_views` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plugin_id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `plugin_id` (`plugin_id`),
  CONSTRAINT `plugin_views_ibfk_1` FOREIGN KEY (`plugin_id`) REFERENCES `plugins` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

INSERT INTO plugin_views VALUES("5","22","Location");
INSERT INTO plugin_views VALUES("6","22","Route");



DROP TABLE IF EXISTS plugins ;

CREATE TABLE `plugins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `schema` tinyint(1) NOT NULL,
  `routing` tinyint(1) NOT NULL,
  `version` varchar(10) NOT NULL,
  `author` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

INSERT INTO plugins VALUES("22","GoogleMaps","1","1","1.0","Patrick Zamzow");
INSERT INTO plugins VALUES("23","StaticText","0","1","1.0","Christoph KrÃ¤mer");



DROP TABLE IF EXISTS roles ;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `roles_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

INSERT INTO roles VALUES("2","3","Guest");
INSERT INTO roles VALUES("3","4","Registered");
INSERT INTO roles VALUES("4","5","Author");
INSERT INTO roles VALUES("5","6","Editor");
INSERT INTO roles VALUES("6","7","Administrator");
INSERT INTO roles VALUES("7","0","Super-Adminsitrator");



DROP TABLE IF EXISTS users ;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL,
  `email` varchar(255) NOT NULL,
  `last_login` datetime NOT NULL,
  `registered` datetime NOT NULL,
  `confirmation_token` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO users VALUES("1","2","christoph","9617b34607ee4ef23ee07b341de5a7ca26ede8c7","christoph@christophkraemer.de","2012-01-02 11:32:57","0000-00-00 00:00:00","","1");
INSERT INTO users VALUES("2","7","test123","e84f100d33cda3f881fe00a334e9dd941dfe2d15","christoph@christophkraemer.de","0000-00-00 00:00:00","2011-12-08 12:26:59","aaaf13160053ec8e18083ecfeb79a2ed34f7f32b","0");
INSERT INTO users VALUES("3","3","blubb","39bd73dac29aa4190d929fc2ce056dcd31bb4585","test@test.de","2011-12-08 12:44:50","2011-12-08 12:44:38","064acc795ba7d0834d6cafad0ff2b65fbf42e676","0");
INSERT INTO users VALUES("4","7","alex_m","b9c66d0192cca90fbc63ccb596b6f35b1b78ae91","alexan.chr.mueller@googlemail.com","2012-01-04 17:29:28","2012-01-04 17:28:46","467af5d818717500bf93cd87ed86e72fee934c85","0");



