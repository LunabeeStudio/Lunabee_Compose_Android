#  Copyright (c) 2025 Lunabee Studio
# 
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
# 
#    http://www.apache.org/licenses/LICENSE-2.0
# 
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
# 
#  presenter_migration_1.8.0.py
#  Lunabee Compose
# 
#  Created by Lunabee Studio / Date - 10/30/2025 - for the Lunabee Compose library.

#!/usr/bin/env python3
import os
import re

IMPORT_LINE = "import android.app.Activity\n"
USE_ACTIVITY_LINE = "        useActivity: (suspend (Activity) -> Unit) -> Unit,\n"  # 8 spaces

# Walk the current directory for *Reducer.kt files
for root, _, files in os.walk("."):
    for filename in files:
        if not filename.endswith(".kt"):
            continue
        filepath = os.path.join(root, filename)

        with open(filepath, "r") as f:
            lines = f.readlines()

        new_lines = []
        in_reduce_function = False
        inserted_use_activity = False  # Track if we actually inserted the line

        i = 0
        while i < len(lines):
            line = lines[i]

            # Detect start of exact 'reduce' function
            if re.match(r'\s*override\s+suspend\s+fun\s+reduce\b\s*\(', line):
                in_reduce_function = True
                new_lines.append(line)
                i += 1
                continue

            # Detect end of reduce function signature
            if in_reduce_function and re.match(r'\s*\): ReduceResult<.*>\s*(\{|=)', line):
                print(f"Updating {filepath} ...")
                # Insert useActivity only if not already present
                prev_line = new_lines[-1] if new_lines else ""
                if USE_ACTIVITY_LINE.strip() not in prev_line:
                    new_lines.append(USE_ACTIVITY_LINE)
                    inserted_use_activity = True
                new_lines.append(line)
                in_reduce_function = False
                i += 1
                continue

            new_lines.append(line)
            i += 1

        # Only add import if useActivity was inserted and import is missing
        if inserted_use_activity and IMPORT_LINE not in new_lines:
            # Find first import line (top of import section)
            first_import_index = None
            for idx, line in enumerate(new_lines):
                if line.startswith("import "):
                    first_import_index = idx
                    break
            if first_import_index is None:
                # No imports in the file, insert after package or at the top
                package_index = 0
                for idx, line in enumerate(new_lines):
                    if line.startswith("package "):
                        package_index = idx + 1
                        break
                new_lines.insert(package_index, IMPORT_LINE)
            else:
                # Insert before the first import
                new_lines.insert(first_import_index, IMPORT_LINE)

        # Write back the file
        with open(filepath, "w") as f:
            f.writelines(new_lines)

print("✅ Replacement complete.")
