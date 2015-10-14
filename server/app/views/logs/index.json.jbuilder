# key :os_version, String
# key :app_version, String
# key :model, String
# key :device, String
# key :contents, String

json.array!(@logs) do |log|
  json.extract! log, :id, :os_version, :app_version, :model, :device, :contents, :updated_at
  json.url log_url(log, format: :json)
end
