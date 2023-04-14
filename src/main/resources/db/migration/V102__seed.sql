INSERT INTO "monster" ("id", "name", "image_url", "attack", "defense", "hp", "speed") VALUES
(1, 'Monster 1', 'https://fsl-assessment-public-files.s3.amazonaws.com/assessment-cc-01/dead-unicorn.png', 50, 40, 30, 25),
(2, 'Old Shark', 'https://fsl-assessment-public-files.s3.amazonaws.com/assessment-cc-01/old-shark.png', 50, 20, 80, 90),
(4, 'Robot Bear', 'https://fsl-assessment-public-files.s3.amazonaws.com/assessment-cc-01/robot-bear.png', 50, 40, 80, 60);

insert into "battle"("id", "monster_a_id", "monster_b_id", "monster_winner") values(1, 1, 2, 1);