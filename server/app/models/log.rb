class Log
  include MongoMapper::Document

  key :os_version, String
  key :app_version, String
  key :model, String
  key :device, String
  key :contents, String

  timestamps!
end
