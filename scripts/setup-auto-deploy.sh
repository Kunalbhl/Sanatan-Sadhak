#!/usr/bin/env bash
# One-time setup: lets GitHub Actions deploy this site to Firebase without any stored key
# (Workload Identity Federation). Run once in Cloud Shell for project sanatan-sadhak.
set -euo pipefail
PROJECT=sanatan-sadhak
REPO=Kunalbhl/Sanatan-Sadhak
SA=github-deployer@$PROJECT.iam.gserviceaccount.com
NUM=$(gcloud projects describe $PROJECT --format='value(projectNumber)')

gcloud services enable iamcredentials.googleapis.com sts.googleapis.com firebasehosting.googleapis.com firebaserules.googleapis.com --project $PROJECT
gcloud iam service-accounts describe $SA --project $PROJECT >/dev/null 2>&1 || \
  gcloud iam service-accounts create github-deployer --display-name="GitHub website deploy" --project $PROJECT
for r in firebasehosting.admin firebaserules.admin serviceusage.serviceUsageConsumer serviceusage.serviceUsageViewer firebase.viewer; do
  gcloud projects add-iam-policy-binding $PROJECT --member="serviceAccount:$SA" --role="roles/$r" --condition=None -q >/dev/null
done
gcloud iam workload-identity-pools describe github --location=global --project $PROJECT >/dev/null 2>&1 || \
  gcloud iam workload-identity-pools create github --location=global --display-name="GitHub" --project $PROJECT
gcloud iam workload-identity-pools providers describe github --workload-identity-pool=github --location=global --project $PROJECT >/dev/null 2>&1 || \
  gcloud iam workload-identity-pools providers create-oidc github --workload-identity-pool=github --location=global --project $PROJECT \
    --issuer-uri="https://token.actions.githubusercontent.com" \
    --attribute-mapping="google.subject=assertion.sub,attribute.repository=assertion.repository" \
    --attribute-condition="assertion.repository=='$REPO'"
gcloud iam service-accounts add-iam-policy-binding $SA --project $PROJECT --role=roles/iam.workloadIdentityUser \
  --member="principalSet://iam.googleapis.com/projects/$NUM/locations/global/workloadIdentityPools/github/attribute.repository/$REPO" >/dev/null
echo "✅ Auto-deploy is set up (project number $NUM). Every push to the website will now publish automatically."
