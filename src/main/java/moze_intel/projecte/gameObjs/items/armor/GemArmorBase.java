package moze_intel.projecte.gameObjs.items.armor;

import javax.annotation.Nonnull;
import moze_intel.projecte.PECore;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public abstract class GemArmorBase extends PEArmor {

	public GemArmorBase(EquipmentSlotType armorType, Properties props) {
		super(GemArmorMaterial.INSTANCE, armorType, props);
	}

	@Override
	public float getFullSetBaseReduction() {
		return 0.05f;
	}

	@Override
	public float getMaxDamageAbsorb(EquipmentSlotType slot, DamageSource source) {
		if (source.isExplosion()) {
			return 0.75f;
		}
		if (slot == EquipmentSlotType.FEET && source == DamageSource.FALL) {
			return 0.15f / getPieceEffectiveness(slot);
		} else if (slot == EquipmentSlotType.HEAD && source == DamageSource.DROWN) {
			return 0.15f / getPieceEffectiveness(slot);
		}
		if (source.isUnblockable()) {
			return 0;
		}
		//If the source is not unblockable, allow our piece to block a certain amount of damage
		if (slot == EquipmentSlotType.HEAD || slot == EquipmentSlotType.FEET) {
			return 0.4f;
		}
		return 0.5f;
	}

	public static boolean hasAnyPiece(PlayerEntity player) {
		return player.inventory.armorInventory.stream().anyMatch(i -> !i.isEmpty() && i.getItem() instanceof GemArmorBase);
	}

	public static boolean hasFullSet(PlayerEntity player) {
		return player.inventory.armorInventory.stream().noneMatch(i -> i.isEmpty() || !(i.getItem() instanceof GemArmorBase));
	}

	private static class GemArmorMaterial implements IArmorMaterial {

		private static final GemArmorMaterial INSTANCE = new GemArmorMaterial();

		@Override
		public int getDurability(@Nonnull EquipmentSlotType slot) {
			return 0;
		}

		@Override
		public int getDamageReductionAmount(@Nonnull EquipmentSlotType slot) {
			if (slot == EquipmentSlotType.FEET) {
				return 3;
			} else if (slot == EquipmentSlotType.LEGS) {
				return 6;
			} else if (slot == EquipmentSlotType.CHEST) {
				return 8;
			} else if (slot == EquipmentSlotType.HEAD) {
				return 3;
			}
			return 0;
		}

		@Override
		public int getEnchantability() {
			return 1;
		}

		@Nonnull
		@Override
		public SoundEvent getSoundEvent() {
			return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
		}

		@Nonnull
		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.EMPTY;
		}

		@Nonnull
		@Override
		public String getName() {
			//Only used on the client
			return PECore.rl("gem_armor").toString();
		}

		@Override
		public float getToughness() {
			return 0.3f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.25F;
		}
	}
}
