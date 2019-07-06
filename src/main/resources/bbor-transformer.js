function initializeCoreMod() {
	Opcodes = Java.type("org.objectweb.asm.Opcodes");
    ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
    InsnList = Java.type("org.objectweb.asm.tree.InsnList");
    LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
    VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
	MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");

    processPacket = ASMAPI.mapMethod("func_148833_a");
    func_228438_a_ = ASMAPI.mapMethod("func_228438_a_");

    return {
        "WorldRenderer": {
            "target": {
              "type": "CLASS",
              "name": "net.minecraft.client.renderer.WorldRenderer",
            },
            "transformer": function(classNode) {
                var methods = classNode.methods;
                for (var method in methods) {
                    var methodNode = methods[method];
                    if (methodNode.name.equals(func_228438_a_)) {
                       inject_WorldRenderer_func_228438_a_(methodNode.instructions);
                       break;
                    }
                }
                return classNode;
            }
        },
        "SCommandListPacket": {
            "target": {
              "type": "CLASS",
              "name": "net.minecraft.network.play.server.SCommandListPacket",
            },
            "transformer": function(classNode) {
                var methods = classNode.methods;
                for (var method in methods) {
                    var methodNode = methods[method];
                    if (methodNode.name.equals(processPacket)) {
                       inject_SCommandListPacket_processPacket(methodNode.instructions);
                       break;
                    }
                }
                return classNode;
            }
        },
        "SSpawnPositionPacket": {
            "target": {
              "type": "CLASS",
              "name": "net.minecraft.network.play.server.SSpawnPositionPacket",
            },
            "transformer": function(classNode) {
                var methods = classNode.methods;
                for (var method in methods) {
                    var methodNode = methods[method];
                    if (methodNode.name.equals(processPacket)) {
                       inject_SSpawnPositionPacket_processPacket(methodNode.instructions);
                       break;
                    }
                }
                return classNode;
            }
        }
    };
}

function inject_WorldRenderer_func_228438_a_(instructions) {
    var newInstructions = new InsnList();
    newInstructions.add(new VarInsnNode(Opcodes.FLOAD, 2));
    newInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                           "com/irtimaled/bbor/forge/ForgeClientInterop",
                                           "render",
                                           "(F)V",
                                           false));
    instructions.insert(newInstructions);
}

function inject_SCommandListPacket_processPacket(instructions) {
    var returnInstruction = getReturnInstructionOrThrow(instructions);

    var newInstructions = new InsnList();
    newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
    newInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                           "com/irtimaled/bbor/forge/ForgeClientInterop",
                                           "registerClientCommands",
                                           "(Lnet/minecraft/client/network/play/IClientPlayNetHandler;)V",
                                           false));
    instructions.insertBefore(returnInstruction, newInstructions);
}

function inject_SSpawnPositionPacket_processPacket(instructions) {
    var returnInstruction = getReturnInstructionOrThrow(instructions);

    var newInstructions = new InsnList();
    newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
    newInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                           "com/irtimaled/bbor/forge/ForgeClientInterop",
                                           "updateWorldSpawnReceived",
                                           "(Lnet/minecraft/network/play/server/SSpawnPositionPacket;)V",
                                           false));
    instructions.insertBefore(returnInstruction, newInstructions);
}

function getReturnInstructionOrThrow(instructions) {
    var size = instructions.size();
    for (var index = 0; index < size; ++index) {
       var instruction = instructions.get(index);
       if (instruction.getOpcode() == Opcodes.RETURN) {
           return instruction;
       }
    }
    throw "Error: Couldn't find injection point \"return\"!";
}