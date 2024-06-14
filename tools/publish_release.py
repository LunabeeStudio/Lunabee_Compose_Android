#  Copyright (c) 2024 Lunabee Studio
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
#  publish_release.py
#  Lunabee Compose
#
#  Created by Lunabee Studio / Date - 3/29/2024 - for the Lunabee Compose library.

import argparse
import sys
import time

import requests


def wait_repository_type(type):
    repository_type = ""
    while repository_type != type:
        time.sleep(10)
        repository_url = f'https://s01.oss.sonatype.org/service/local/staging/repository/{repositoryId}'
        repository_response = requests.get(repository_url, headers={'Accept': 'application/json'}, auth=(username, password))
        if not (200 <= repository_response.status_code < 300):
            sys.exit(f"Request 'repository' failed with status code: {str(repository_response.status_code)}")
        repository_type = repository_response.json()["type"]


parser = argparse.ArgumentParser(
    description='''
    Release staged build on sonatype''',
    formatter_class=argparse.RawTextHelpFormatter,
)
parser.add_argument(
    "-u",
    "--username",
    help="Sonatype username",
    required=True,
    type=str,
)
parser.add_argument(
    "-p",
    "--password",
    help="Sonatype password",
    required=True,
    type=str,
)

args = parser.parse_args()

username = args.username
password = args.password

profile_url = 'https://s01.oss.sonatype.org/service/local/staging/profile_repositories'
profile_response = requests.get(profile_url, headers={'Accept': 'application/json'}, auth=(username, password))
if not (200 <= profile_response.status_code < 300):
    sys.exit(f"Request 'profile_repositories' failed with status code: {str(profile_response.status_code)}")
profile_response_data = profile_response.json()
profileId = profile_response_data["data"][-1]["profileId"]
repositoryId = profile_response_data["data"][-1]["repositoryId"]
print(f"Found profileId = {profileId}, repositoryId = {repositoryId}")

print("Prepare release for publication")
finish_url = f'https://s01.oss.sonatype.org/service/local/staging/profiles/{profileId}/finish'
finish_xml_data = (f'<promoteRequest><data><stagedRepositoryId>{repositoryId}</stagedRepositoryId><description>Release last '
                   f'version</description></data></promoteRequest>')
finish_response = requests.post(finish_url,
                                headers={'Content-Type': 'application/xml', 'Accept': 'application/json'},
                                data=finish_xml_data,
                                auth=(username, password))
if not (200 <= finish_response.status_code < 300):
    sys.exit(f"Request 'finish' failed with status code: {str(finish_response.status_code)}")

print("Waiting for Sonatype verifications")
wait_repository_type("closed")

print("Publishing release on Maven Central")
promote_url = f'https://s01.oss.sonatype.org/service/local/staging/profiles/{profileId}/promote'
promote_xml_data = (f'<promoteRequest><data><stagedRepositoryId>{repositoryId}</stagedRepositoryId><description>Release last '
                    f'version</description></data></promoteRequest>')
promote_response = requests.post(promote_url,
                                 headers={'Content-Type': 'application/xml', 'Accept': 'application/json'},
                                 data=finish_xml_data,
                                 auth=(username, password))
if not (200 <= promote_response.status_code < 300):
    sys.exit(f"Request 'promote' failed with status code: {str(promote_response.status_code)}")

print("Waiting for Sonatype release")
wait_repository_type("released")

print(f"Dropping repository {repositoryId}")
drop_url = f'https://s01.oss.sonatype.org/service/local/staging/profiles/{profileId}/drop'
drop_xml_data = (f'<promoteRequest><data><stagedRepositoryId>{repositoryId}</stagedRepositoryId><description>Release last '
                 f'version</description></data></promoteRequest>')
drop_response = requests.post(drop_url,
                              headers={'Content-Type': 'application/xml', 'Accept': 'application/json'},
                              data=finish_xml_data,
                              auth=(username, password))
if not (200 <= drop_response.status_code < 300):
    sys.exit(f"Request 'drop' failed with status code: {str(drop_response.status_code)}")

print("Release is published and will be available on Maven Central in a few minutes")
