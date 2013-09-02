INSERT INTO `User` VALUES (1,'','chico.sokol@gmail.com','Francisco Sokol','9c2f5ce0472220c016a8a77822c22d211ab9233a7083bbe009b0db86380b6135','ADMINISTRATOR','','IME USP');


INSERT INTO `StatisticalTest` VALUES (1,'set1 <- #set1#\nset2 <- #set2#\nt1 = wilcox.test(set1, set2, paired = TRUE)\nt1','Paired Wilcoxon Signed-Rank Test',NULL);

INSERT INTO `StatisticalTest` VALUES (2,'set1 <- #set1#\nset2 <- #set2#\nt1 = wilcox.test(set1, set2, paired = FALSE)\nt1','Non-Paired Wilcoxon Signed-Rank Test',NULL);

INSERT INTO `StatisticalTest` VALUES (3,'set1 <- #set1#\nset2 <- #set2#\nt1 = t.test(set1, set2, paired = TRUE)\nt1','Paired T-Student Test',NULL);

INSERT INTO `StatisticalTest` VALUES (4,'set1 <- #set1#\nset2 <- #set2#\nt1 = t.test(set1, set2, paired = FALSE)\nt1','Non-Paired T-Student Test',NULL);

INSERT INTO `StatisticalTest` VALUES (5,'set1 <- #set1#\nset2 <- #set2#\nt1 = cor.test(set1, set2, method="pearson")\nt1','Pearson Correlation',NULL);

INSERT INTO `StatisticalTest` VALUES (6,'set1 <- #set1#\nset2 <- #set2#\nt1 = cor.test(set1, set2, method="spearman")\nt1','Spearman Correlation',NULL);
