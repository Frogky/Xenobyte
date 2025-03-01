package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class FTBSatchelDupe extends CheatModule {

    private Random random = new Random();

    public FTBSatchelDupe() {
        super("FTBSatchelDupe", Category.MODS, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        try {
            if (Class.forName("cofh.thermalexpansion.gui.container.ContainerSatchel").isInstance(utils.player().openContainer)) {
                // Obfuscation: Dynamically generate class and method names
                String messageClass = "ftb.lib.mod.net.".concat("Mess").concat("ageC").concat("lien").concat("tIte").concat("mAct").concat("ion");
                String sendMethod = "send".concat("To").concat("Ser").concat("ver");

                // Packet data with randomized values and encoded action string
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("rnd", random.nextInt());
                nbt.setString("data", encodeString("dupeRequest"));

                Class.forName("ftb.lib.api.net.MessageLM").getMethod(sendMethod).invoke(
                        Class.forName(messageClass).getConstructor(String.class, NBTTagCompound.class)
                                .newInstance(encodeString("dupe"), nbt)
                );

                int dropCount = (int) Class.forName("cofh.thermalexpansion.item.ItemSatchel").getMethod("getStorageIndex", ItemStack.class).invoke(null, utils.item());

                // Randomize dropping order and introduce variable delays
                intslots = new int[dropCount * 9];
                for (int i = 0; i < slots.length; i++) {
                    slots[i] = utils.mySlotsCount() + i;
                }
                shuffleArray(slots);

                for (int slot : slots) {
                    utils.dropSlot(slot, true);
                    Thread.sleep(random.nextInt(100) + 20); // Delay between 20 and 120 ms
                }

                utils.player().closeScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Simple string encoding (can be more sophisticated)
    private String encodeString(String str) {
        charchars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += 1;
        }
        return new String(chars);
    }

    // Fisher-Yates shuffle algorithm
    private void shuffleArray(intarray) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    @Override
    public boolean inGuiPerform() {
        return true;
    }

    @Override
    public boolean isWorking() {
        return Loader.isModLoaded("ThermalExpansion") && Loader.isModLoaded("FTBL");
    }

    @Override
    public String moduleDesc() {
        return lang.get("Open any ThermalExpansion bag with Items and press keybind of function", "Открыть любую сумку из ThermalExpansion с вещами и нажать кейбинд функции");
    }
}
