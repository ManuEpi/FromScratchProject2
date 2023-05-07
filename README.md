# FromScratchProject
Création d'un projet from scratch qui a pour but de récupérer une liste de news via une API (https://newsapi.org/), l'afficher, et permettre de cliquer sur un élément afin d'y voir son détail

1) Utilisation des librairies : 

    -  Dagger Hilt -> Injection de dépendance -> Choix par habitude

    - Retrofit -> Networking

    - Timber -> Pour la partie logs

    - Coil -> Afin de pouvoir charger une image à partir d'une URL dans une Imageview -> J'utilise d'habitude Glide mais pour mon besoin Coil suffisait et faisait le travail simplement

2) Architecture MVVM, avec Clean Architecture en respectant le découpage suivant : 

    - DATA - Data-impl -> Récupération de la data reçu par l'appel de service à l'API
    
    - DOMAIN - Domain-impl -> Traitement de la donnée
    
    - UI -> ViewModel et Ecrans -> Récupération de la data depuis la partie Domain, et affichage de celle-ci
    
    - Entity -> Stockage de la donnée séléctionnée, afin de l'avoir sur l'écran de détail simplement via un Flow, sans avoir a passer des paramètres entre les écrans
    
La partie UI demande à récuperer de manière asynchrone la liste de news, elle va contacter la partie USECASE qui elle va contacter la partie REPOSITORY. Et ensuite le REPOSITORY va donner l'info au USECASE qui va le redescendre à la partie UI, application donc de la "Separation of Concerns"

J'ai choisi cette architecture car c'est celle que je maîtrise le mieux et que bien que redondante (car création de nombreux fichiers), la clean architecture est respectée.


3) Problèmes rencontrés : 
    - Très gros soucis avec Android Studio Flamingo et l'injection de dépendances, j'ai eu des problèmes dans mes modules et j'y ai perdu pas mal de temps !
    - Quelques soucis avec NewsApi car il fallait passer le paramètre "q" et je pensais qu'il fallait uniquement l'apiKey
    
4) Améliorations possibles envisagées mais non inclues :
    - Mise en place de Shimmer pour ajouter un chargement lors de l'affichage de la liste
    - Stockage dans l'entité de la liste de news


Temps passé : environ 10 heures
