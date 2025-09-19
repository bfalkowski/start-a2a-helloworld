#!/usr/bin/env python3
"""
A2A HelloWorld Agent Card Fetcher
Simple Python script to get agent card information
"""

import requests
import json
import sys

def get_agent_card(base_url="https://a2a-helloworld-1dd6ef1d53ae.herokuapp.com"):
    """Get the agent card from the deployed A2A agent"""
    try:
        response = requests.get(f"{base_url}/agent/card", timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"❌ Error fetching agent card: {e}")
        return None

def get_agent_health(base_url="https://a2a-helloworld-1dd6ef1d53ae.herokuapp.com"):
    """Get the agent health status"""
    try:
        response = requests.get(f"{base_url}/agent/health", timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"❌ Error fetching agent health: {e}")
        return None

def call_jsonrpc_method(method, params=None, base_url="https://a2a-helloworld-1dd6ef1d53ae.herokuapp.com"):
    """Call a JSON-RPC method on the agent"""
    try:
        payload = {
            "jsonrpc": "2.0",
            "method": method,
            "params": params or {},
            "id": 1
        }
        response = requests.post(
            f"{base_url}/jsonrpc",
            json=payload,
            headers={"Content-Type": "application/json"},
            timeout=10
        )
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"❌ Error calling JSON-RPC method '{method}': {e}")
        return None

def main():
    print("🤖 A2A HelloWorld Agent Card Fetcher")
    print("=" * 40)
    
    # Get agent card
    print("📋 Fetching agent card...")
    agent_card = get_agent_card()
    if agent_card:
        print("✅ Agent Card:")
        print(json.dumps(agent_card, indent=2))
    else:
        print("❌ Failed to get agent card")
        sys.exit(1)
    
    print("\n" + "=" * 40)
    
    # Get health status
    print("💚 Checking agent health...")
    health = get_agent_health()
    if health:
        print("✅ Health Status:")
        print(json.dumps(health, indent=2))
    
    print("\n" + "=" * 40)
    
    # Test JSON-RPC greeting
    print("👋 Testing JSON-RPC greeting...")
    greeting = call_jsonrpc_method("greeting")
    if greeting:
        print("✅ Greeting Response:")
        print(json.dumps(greeting, indent=2))
    
    print("\n✅ All tests completed successfully!")

if __name__ == "__main__":
    main()
