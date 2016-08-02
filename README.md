# BBJumpPads
Jump pads plugin for beanblockz.com

# Commands
| Command        | Description                  | Permission         |
| -------------- | ---------------------------- | ------------------ |
| /jp            | Shows command usage          | none               |
| /jp version    | Shows current plugin version | bbjumppads.version |
| /jp reload     | Reloads the messages config  | bbjumppads.reload  |

# Permissions
(Excluding command permissions that are listed above)

| Node                       | Description                   |
| -------------------------- | ----------------------------- |
| bbjumppads.jumppads.use    | Allows the use of jump pads   |
| bbjumppads.trampolines.use | Allows the use of trampolines |

# Building
Uses maven for building. Run the following command to create a jar:
```mvn clean package```