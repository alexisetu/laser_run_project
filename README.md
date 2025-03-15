# laser_run_project

Dépôt git : https://github.com/alexisetu/laser_run_project/

## Informations pour la notation
- **Branche pour la notation** : main
- **Étudiant** : GILBERT Alexis

## I/ Maquette

À retrouver sur https://www.figma.com/design/IisAQoPZkDA2J4EH6Jpdqf/Untitled?node-id=0-1&p=f&t=Dn2NdtibziP9AlRh-0

## II/ Fonctionnalités

### Fonctionnalités implémentées et fonctionnelles

- **Sélection de catégorie** : L'application permet de sélectionner une catégorie (Senior, M40, M60) qui détermine les paramètres de la course (distance initiale, nombre de tours, distance de tir).
- **Écran de départ** : Affichage des informations de la catégorie sélectionnée et bouton pour démarrer la course.
- **Chronométrage** : Deux chronomètres sont implémentés, un pour le temps global et un pour le temps du tour en cours.
- **Navigation entre les activités** :
  - Si l'on appuie sur 'Courir' on lance l'interface de la course
  - Le bouton 'Courir' est désactivé si la catégorie n'est pas donnée dans les paramètres
  - Appuyer sur 'départ' pour lancer le chrono et basculer vers la page de la course
  - Appuyer sur 'arrivée au stand' pour basculer vers la page du temps de tir
  - Appuyer sur '5 cibles touchée, 4, 3, 2, 1 ou 0' pour signaler le nombre de cibles touchées
  - Lors du dernier tour, le bouton devient 'fin de course' et on bascule vers la page de résultats
- **Enregistrement des temps** : Un chrono est enregistré pour chaque tour et un chrono total est enregistré
- **Alerte visuelle** : Une alerte visuelle est affichée lorsque le temps sur le stand de tir dépasse 50 secondes
- **Écran de résultats** : Affichage des temps de course, des temps de tir, et des statistiques de tir (cibles touchées, temps moyen sur le stand)

### Fonctionnalités non implémentées ou partiellement fonctionnelles

- **Calcul de la vitesse moyenne** : Cette fonctionnalité a été retirée en raison de difficultés techniques.
- **Tests automatisés** : Les tests n'ont pas pu être implémentés en raison de contraintes matérielles.
- **Historique des parties** : Cette fonctionnalité n'a pas été implémentée.
- **Géolocalisation** : Cette fonctionnalité n'a pas été implémentée.
- **Statistiques avancées** : Cette fonctionnalité n'a pas été implémentée.

En effet, ayant un ordinateur personnel très très limité surtout pour lancer un émulateur, il a été très compliqué pour moi d'avancer sur le projet

## III/ Bugs connus

Aucun bug connu à ce jour.

## IV/ Choix techniques et librairies utilisées

- **Architecture** : L'application suit une architecture simple basée sur des activités Android.
- **Stockage des données** : Les données sont transmises entre les activités via des intents.
- **Interface utilisateur** : Utilisation des composants standard d'Android (TextView, Button, Chronometer, etc.).
- **Gestion des états** : Les états de l'application sont gérés à travers les cycles de vie des activités.
- **Internationalisation** : Toutes les chaînes de caractères sont centralisées dans le fichier strings.xml pour faciliter la localisation.

## V/ Limitations et perspectives d'amélioration

En raison de contraintes matérielles (outils personnels très lents pour faire fonctionner Android Studio avec un émulateur), le développement s'est concentré sur le MVP (Minimum Viable Product). Plusieurs améliorations pourraient être apportées dans le futur :

- Implémentation du calcul de la vitesse moyenne
- Ajout de tests unitaires et d'instrumentation
- Développement des fonctionnalités d'historique et de statistiques
- Amélioration de l'interface utilisateur
- Mise en place d'une base de données locale pour stocker les résultats des courses