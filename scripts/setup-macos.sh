#!/usr/bin/env bash
set -euo pipefail

echo "Installing development tools for Library Management project..."
xcode-select --install || true

# Homebrew
if ! command -v brew >/dev/null 2>&1; then
  echo "Installing Homebrew..."
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
  eval "$(/opt/homebrew/bin/brew shellenv)"
fi

# Java + Maven
brew install --cask temurin17
brew install maven

# Node (via nvm)
brew install nvm
mkdir -p ~/.nvm
{
  echo 'export NVM_DIR="$HOME/.nvm"'
  echo '[ -s "/opt/homebrew/opt/nvm/nvm.sh" ] && . "/opt/homebrew/opt/nvm/nvm.sh"'
} >> ~/.zshrc
source ~/.zshrc

nvm install 18
nvm use 18
npm i -g @angular/cli@17

echo "✅ Setup complete.
Run the backend with:
  cd backend && ./mvnw spring-boot:run
Run the frontend with:
  cd frontend && npm ci && ng serve --proxy-config proxy.conf.json"
