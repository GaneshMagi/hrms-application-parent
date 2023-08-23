import logging
import os
import shutil

from Email_process.constants import DOWNLOADS, PROCESSED
from Database.module import Candidate


class Folders:
    def __init__(self, schema_name):
        self.c_id = Candidate().get_id(schema_name)

    def move_all_files(self, source_folder, destination_folder):
        try:
            if self.c_id is not None:
                c_id = self.c_id + 1
                c_id = str(c_id)
            else:
                c_id = str(1)
            file_list = os.listdir(source_folder)

            for file in file_list:
                source_file_path = os.path.join(source_folder, file)
                new_file_name = destination_folder + "/" + str(c_id) + file

                destination_file_path = os.path.join(destination_folder, new_file_name)
                shutil.move(source_file_path, destination_file_path)
                print(f"moved {file} ")
                return new_file_name
        except Exception as e:
            print("error:", str(e))

    def move_file(self):
        """ this method renames current file path and move file into processed folder"""
        try:
            if self.c_id is not None:
                c_id = self.c_id + 1
                c_id = str(c_id)
            else:
                c_id = str(1)

            # get list of files names
            files = os.listdir(DOWNLOADS)  # present file path
            for file_name in files:

                # Current file name and path
                current_file = file_name
                current_path = DOWNLOADS

                # remove spaces in file name
                new_file = current_file.replace(" ", "")
                new_file = new_file.replace("'", "")
                # New file name with ID
                new_file = f"{c_id}{new_file}"

                # Path to the processed folder
                processed_folder = PROCESSED

                # New path for the file in the processed folder
                new_path = os.path.join(processed_folder, new_file)
                print(new_path)
                # replace backslashes with forward slashes in path
                new_path = new_path.replace('\\', '/')
                # Rename the file and move to folder
                os.rename(os.path.join(current_path, current_file), new_path)
                """path = "\\\\WINDOWS_SERVER\processed" + '\\' + new_file
                print(path)"""
                # Print the new file path
                print("New file path:", new_path)
                if new_path:
                    return new_path
                else:
                    file_path = Folders("public").move_all_files(DOWNLOADS, PROCESSED)
                    return file_path

        except Exception as e:
            logging.error("Error to rename file path", str(e))
            file_path = Folders("public").move_all_files(DOWNLOADS, PROCESSED)
            return file_path
#Folders().move_file()



