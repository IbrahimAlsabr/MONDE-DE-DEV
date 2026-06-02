-- ==============================================================
-- USERS
-- Credentials (email or username / password):
--   user@user.com   | user   → 	
--   alice@example.com | alice → Alice1234!
--   bob@example.com   | bob   → Bob1234!
-- ==============================================================
INSERT INTO users (email, username, password) VALUES
  ('user@user.com',      'user',  '$2b$10$98tPxoxueKYIHMSdG2IHNuQLg4YnsLjKwes//j3CSx50fp/rNdGMy'),
  ('alice@example.com',  'alice', '$2b$10$BQAoWlMxaOMzxjf.u1z50O5wIj.qOwffgspHO4ovqJZ6HhbY7N59m'),
  ('bob@example.com',    'bob',   '$2b$10$0AUNh/xZsqYOF53VIh9WauvWrgDb5iqUkvW38/UtphQDKurXJ93qi');

-- ==============================================================
-- TOPICS (thèmes du monde de la programmation)
-- ==============================================================
INSERT INTO topics (name, description) VALUES
  ('JavaScript',             'Langage incontournable du web, côté client et serveur (Node.js). Couvre ES6+, les Promises, async/await, les frameworks comme React et Angular.'),
  ('Java',                   'Langage orienté objet robuste et portable. Couvre Spring Boot, les streams, la JVM, les design patterns et les bonnes pratiques de développement.'),
  ('Python',                 'Langage polyvalent idéal pour la data science, l''automatisation et le web. Couvre NumPy, Pandas, Django, Flask et l''apprentissage automatique.'),
  ('DevOps',                 'Pratiques et outils pour automatiser et améliorer le cycle de développement logiciel. Couvre Docker, Kubernetes, CI/CD, et l''intégration continue.'),
  ('Architecture logicielle','Principes et patterns pour concevoir des systèmes robustes et évolutifs. Couvre microservices, monolithes, DDD, SOLID et les patrons de conception.');

-- ==============================================================
-- POSTS (articles)
-- ==============================================================
INSERT INTO posts (title, content, created_at, user_id, topic_id) VALUES
  (
    'Introduction à JavaScript',
    'JavaScript est un langage de programmation qui rend les pages web interactives. Avec ES6 et les versions modernes, JavaScript est devenu très puissant pour le développement front-end et back-end via Node.js.',
    '2026-04-01 10:00:00', 1, 1
  ),
  (
    'Les Promises en JavaScript',
    'Les Promises permettent de gérer les opérations asynchrones en JavaScript. Elles représentent une valeur qui peut être disponible maintenant, dans le futur, ou jamais. Combinées à async/await, elles rendent le code asynchrone lisible.',
    '2026-04-10 14:30:00', 2, 1
  ),
  (
    'Spring Boot : démarrer rapidement',
    'Spring Boot simplifie la création d''applications Java en proposant une auto-configuration. Il suffit de déclarer les dépendances nécessaires et Spring Boot configure le contexte applicatif pour vous.',
    '2026-04-15 09:15:00', 1, 2
  ),
  (
    'L''API Stream en Java 8',
    'L''API Stream de Java 8 permet de traiter des collections de données de façon déclarative. Combinée aux expressions lambda, elle rend le code plus lisible et concis tout en facilitant le parallélisme.',
    '2026-04-20 11:00:00', 3, 2
  ),
  (
    'Python pour la data science',
    'Python est le langage de référence pour la data science grâce à des bibliothèques comme NumPy, Pandas et Scikit-learn. Son écosystème riche facilite l''analyse et la visualisation de données.',
    '2026-04-25 16:00:00', 2, 3
  ),
  (
    'Docker : conteneurisation d''applications',
    'Docker permet d''empaqueter une application et ses dépendances dans un conteneur isolé. Cela garantit que l''application fonctionne de façon identique quel que soit l''environnement cible.',
    '2026-05-01 13:45:00', 3, 4
  ),
  (
    'Microservices vs Architecture Monolithique',
    'Les architectures microservices découpent une application en petits services indépendants. Cette approche améliore la scalabilité et la maintenabilité, mais complexifie la communication et le déploiement.',
    '2026-05-10 10:30:00', 1, 5
  );

-- ==============================================================
-- SUBSCRIPTIONS (abonnements)
-- ==============================================================
INSERT INTO subscriptions (user_id, topic_id) VALUES
  (1, 1), -- user  → JavaScript
  (1, 2), -- user  → Java
  (2, 2), -- alice → Java
  (2, 3), -- alice → Python
  (3, 1), -- bob   → JavaScript
  (3, 4); -- bob   → DevOps

-- ==============================================================
-- COMMENTS (commentaires)
-- ==============================================================
INSERT INTO comments (content, created_at, post_id, user_id) VALUES
  ('Super article ! Les Promises sont vraiment utiles pour éviter le callback hell.', '2026-04-11 08:00:00', 2, 1),
  ('Merci pour cet article, j''ai enfin compris le concept des Promises !',           '2026-04-11 10:30:00', 2, 3),
  ('Spring Boot est impressionnant pour bootstrapper un projet rapidement.',           '2026-04-16 09:00:00', 2, 2),
  ('Les streams Java ont changé ma façon d''écrire du code. Excellent résumé !',      '2026-04-21 14:00:00', 4, 1),
  ('Docker a vraiment simplifié notre workflow de déploiement en équipe.',             '2026-05-02 11:30:00', 6, 1),
  ('Très bonne comparaison. Les microservices ne sont pas toujours la bonne réponse.',  '2026-05-11 09:00:00', 7, 2);
