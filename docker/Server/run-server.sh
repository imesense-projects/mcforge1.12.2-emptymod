#!/usr/bin/env sh

# Check license
if [ "$EULA" = "true" ]; then
    echo "eula=true" > eula.txt
else
    echo "You need to accept the EULA by setting EULA=true"
    exit 1
fi

# Run server
java \
    -Xms${MIN_MEMORY} \
    -Xmx${MAX_MEMORY} \
    -jar forge.jar \
        nogui
