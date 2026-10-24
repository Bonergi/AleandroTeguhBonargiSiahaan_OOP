package com.bonergi.frontend;

import com.bonergi.frontend.objects.enemies.Boss;
import com.bonergi.frontend.objects.enemies.Fairy;
import com.bonergi.frontend.objects.items.Item;
import com.bonergi.frontend.objects.items.ItemType;
import com.bonergi.frontend.objects.Player;
import com.bonergi.frontend.objects.enemies.Enemy;
import com.bonergi.frontend.objects.bullets.Bullet;

public class Test {
    public static void main(String[] args) {
        // ==========================================
        // MODULE 1: BASIC CLASSES & OBJECT INTERACTION
        // ==========================================
        System.out.println("=== TOUHOU OOP PRACTICUM - MODULE 1: BASIC CLASSES & OBJECT INTERACTION ===");

        // Object instantiation for Player and Enemy
        Player mainPlayer = new Player("Reimu Hakurei", 100, 15, 3);
        Enemy targetEnemy = new Enemy("Cirno (Stage 2 Boss)", 50);

        System.out.println("\n--- Initial Battle State ---");
        System.out.println("Player: " + mainPlayer.getName() + " | HP: " + mainPlayer.getHp() + " | Power: " + mainPlayer.getPower() + " | SpellCards: " + mainPlayer.getSpellCards());
        System.out.println("Enemy: " + targetEnemy.getName() + " | HP: " + targetEnemy.getHp());

        System.out.println("\n--- Turn 1: Player Shoots Enemy ---");
        mainPlayer.shoot(targetEnemy);

        System.out.println("\n--- Turn 2: Enemy Counter-attacks ---");
        targetEnemy.attack(mainPlayer, 30);

        System.out.println("\n--- Turn 3: Player Shoots Enemy Finishing Blow ---");
        mainPlayer.shoot(targetEnemy);

        System.out.println("\n--- Turn 4: Enemy Deals Fatal Damage to Player ---");
        targetEnemy.attack(mainPlayer, 80);

        System.out.println("\n=== Battle Simulation Complete ===");


        // ==========================================
        // MODULE 2: ENCAPSULATION, INHERITANCE & SCORE SYSTEM
        // ==========================================
        System.out.println("\n\n=== TOUHOU OOP PRACTICUM - MODULE 2: ENCAPSULATION, INHERITANCE & SCORE SYSTEM ===");

        // Polymorphic object setup
        Player mainPlayer2 = new Player("Reimu Hakurei", 100, 15, 3);
        Fairy stageFairy = new Fairy("Stage 1 Fairy", 20);
        Boss stageBoss = new Boss("Cirno (Stage 2 Boss)", 150);
        Item scoreItem = new Item(200, 450, 12, 12, 120f, "Point Item", 1000L);

        System.out.println("\n--- Testing Encapsulation & Inheritance ---");
        System.out.println("Player: " + mainPlayer2.getName() + " | Position: (" + mainPlayer2.getX() + ", " + mainPlayer2.getY() + ")");
        System.out.println("Fairy:  " + stageFairy.getName() + " | Defeat Worth: " + stageFairy.getScoreValue() + " pts");
        System.out.println("Boss:   " + stageBoss.getName() + " | Defeat Worth: " + stageBoss.getScoreValue() + " pts | Size: " + stageBoss.getWidth() + "x" + stageBoss.getHeight());
        System.out.println("Item:   " + scoreItem.getItemType() + " | Value: " + scoreItem.getScoreValue() + " pts | Speed: " + scoreItem.getSpeed());

        System.out.println("\n--- Testing Item Movement Update ---");
        System.out.println("Initial Item Y: " + scoreItem.getY());
        scoreItem.update(0.5f);
        System.out.println("Item Y after 0.5s update: " + scoreItem.getY() + " (linear downward movement)");

        System.out.println("\n--- Testing Scoring System ---");
        System.out.println("Initial Score: " + mainPlayer2.getScore());
        mainPlayer2.shoot(stageFairy);
        mainPlayer2.collectItem(scoreItem);
        mainPlayer2.shoot(stageBoss);
        System.out.println("Final Score: " + mainPlayer2.getScore() + " pts");

        System.out.println("\n=== Module 2 Test Completed Successfully ===");


        // ==========================================
        // PRE-CS MODULE 4: SANITY CHECK
        // ==========================================
        System.out.println("\n\n=== PRE-CS MODULE 4: SANITY CHECK ===");
        Player playerMod4 = new Player("Reimu Hakurei", 100, 15, 3);
        Bullet firedBullet = playerMod4.shootBullet();
        System.out.println("Bullet created at: (" + firedBullet.getX() + ", " + firedBullet.getY() + ") | Damage: " + firedBullet.getDamage());
        firedBullet.update(0.1f);
        System.out.println("Bullet Y after 0.1s: " + firedBullet.getY());
        System.out.println("Is bullet off screen? " + firedBullet.isOffScreen(640, 480));
    }
}
