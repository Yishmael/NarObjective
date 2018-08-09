package entities.units;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import entities.Entity;
import entities.objects.Item;
import misc.Consts;
import misc.MainGame;
import types.ItemType;

public abstract class Unit extends Entity {
    protected float speed = 2;
    protected Item[] inventory = new Item[9];
    protected int health = 75, maxHealth = 100, mana = 50, maxMana = 100;
    protected ItemType equippedItemType = ItemType.nil;
    protected int equippedItemIndex = -1;
    protected long lastAttackTime;
    protected int damage;
    protected int attackCooldown = 999999;
    protected boolean alive = true;

    public Unit(String imagePath, float x, float y, float speed, int width, int height)
            throws SlickException {
        super(imagePath, x, y, width, height);
        this.speed = speed;
    }

    public Unit(String imagePath, float x, float y, float speed) throws SlickException {
        this(imagePath, x, y, speed, (int) Consts.TILE_WIDTH, (int) Consts.TILE_HEIGHT);
    }

    public void addItem(Item item) {
        if (!isInventoryFull()) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] == null) {
                    inventory[i] = item;
                    break;
                }
            }
        }
    }

    public String attack() {
        if (isAttackReady()) {
            lastAttackTime = MainGame.getTime();
            return "damage " + damage;
        }
        return null;
    }

    public void damage(int amount) {
        health = Math.max(0, health - amount);
        alive = health > 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroyItem(String name) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                if (inventory[i].getName().equals(name)) {
                    if (i == equippedItemIndex) {
                        unequipItem();
                    }
                    inventory[i] = null;
                    break;
                }
            }
        }
    }

    public void destroyItemAt(int i) {

        if (inventory.length > i) {
            if (i == equippedItemIndex) {
                unequipItem();
            }
            inventory[i] = null;
        }
    }

    public void equipItem(int i) {
        if (inventory.length > i && inventory[i] != null) {
            equippedItemType = inventory[i].getItemType();
            equippedItemIndex = i;
        } else {
            equippedItemIndex = -1;
            equippedItemType = ItemType.nil;
        }

    }

    public int getDamage() {
        return damage;
    }

    public Item getEquippedItem() throws SlickException {
        if (equippedItemIndex != -1) {
            return inventory[equippedItemIndex];
        } else {
            return new Item(ItemType.nil, 0, 0);
        }
    }

    public int getEquippedItemIndex() {
        return equippedItemIndex;
    }

    public ItemType getEquippedItemType() {
        return equippedItemType;
    }

    public int getHealth() {
        return health;
    }

    public Item getItem(String name) {
        for (Item item: inventory) {
            if (item != null) {
                if (item.getName().equals(name)) {
                    return item;
                }
            }
        }
        return null;
    }

    public Item getItemAt(int i) {
        if (inventory.length > i) {
            return inventory[i];
        } else {
            return null;
        }
    }

    public Item[] getItems() {
        return inventory;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean hasItem(String string) {
        for (Item item: inventory) {
            if (item != null) {
                if (item.getName().equals(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAttackReady() {
        return MainGame.getTime() - lastAttackTime >= attackCooldown;
    }

    public boolean isInventoryFull() {
        for (Item item: inventory) {
            if (item == null) {
                return false;
            }
        }
        return true;
    }

    public void move(float dx, float dy) {
        x += speed * dx;
        y += speed * dy;

        if (dx != 0) {
            image.setRotation(90 * -Math.signum(dx));
        }
        if (dy != 0) {
            image.setRotation(90 * (1 - Math.signum(dy)));
        }

        if (dy > 0) {
            facing = 0;
        } else if (dy < 0) {
            facing = 2;
        }

        if (dx > 0) {
            facing = 1;
        } else if (dx < 0) {
            facing = 3;
        }
    }

    public void moveDown(float dy) {
        y += speed * dy;
        image.setRotation(0);
        facing = 0;
    }

    public void moveLeft(float dx) {
        x -= speed * dx;
        image.setRotation(90);
        facing = 3;
    }

    public void moveRight(float dx) {
        x += speed * dx;
        image.setRotation(-90);
        facing = 1;
    }

    public void moveUp(float dy) {
        y -= speed * dy;
        image.setRotation(180);
        facing = 2;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void unequipItem() {
        equippedItemType = ItemType.nil;
        equippedItemIndex = -1;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        float drawX, drawY;
        if (Consts.SNAPPING) {
            drawX = x - x % Consts.TILE_WIDTH;
            drawY = y - y % Consts.TILE_HEIGHT;
        } else {
            drawX = x;
            drawY = y;
        }
        g.fillRect(drawX, drawY - 3, 32f * health / maxHealth, 3);
        super.draw(g);
    }

    public String useItem() {
        // for (int i = 0; i < inventory.length; i++) {
        // if (inventory[i] != null) {
        // Item item = inventory[i];
        // inventory[i] = null;
        // return item.use();
        // }
        // }
        return null;
    }

}
