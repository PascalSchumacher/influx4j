package com.zaxxer.influx4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Represents a Query against Influxdb.
 *
 * @author stefan.majer [at] gmail.com
 *
 */
public class Query {
   private final String command;
   private final String database;
   private final boolean requiresPost;

   public static class Builder {
      private String command;
      private String database;
      private boolean requiresPost;

      private Builder() {
      }

      public Builder setCommand(final String command) {
         this.command = command;
         return this;
      }

      public Builder setDatabase(final String database) {
         this.database = database;
         return this;
      }

      public Builder setRequiresPost(final boolean postRequired) {
         this.requiresPost = postRequired;
         return this;
      }

      public Query build() {
         return new Query(command, database, requiresPost);
      }
   }

   public static Builder builder() {
      return new Builder();
   }

    /**
     * @param command the query command
     * @param database the database to query
     * @param requiresPost true if the command requires a POST instead of GET to influxdb
     */
   private Query(final String command, final String database, final boolean requiresPost) {
      super();
      this.command = command;
      this.database = database;
      this.requiresPost = requiresPost;
   }

   /**
    * @return the command
    */
   public String getCommand() {
      return this.command;
   }

   /**
    * @return url encoded command
    */
   public String getCommandWithUrlEncoded() {
      return encode(this.command);
   }

   /**
    * @return the database
    */
   public String getDatabase() {
      return this.database;
   }

   public boolean requiresPost() {
      return requiresPost;
   }

   @SuppressWarnings("checkstyle:avoidinlineconditionals")
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((command == null) ? 0 : command.hashCode());
      result = prime * result + ((database == null) ? 0 : database.hashCode());
      result = prime * result + (requiresPost ? 0 : 1);
      return result;
   }

   @SuppressWarnings("checkstyle:needbraces")
   @Override
   public boolean equals(final Object obj) {
      if (this == obj)
         return true;
      if (obj == null || !(obj instanceof Query))
         return false;

      Query other = (Query) obj;
      if (command == null) {
         if (other.command != null)
            return false;
      } else if (!command.equals(other.command))
         return false;

      if (database == null) {
         if (other.database != null)
            return false;
      } else if (!database.equals(other.database))
         return false;

      return true;
   }

   /**
    * Encode a command into {@code x-www-form-urlencoded} format.
    * @param command
    *                  the command to be encoded.
    * @return a encoded command.
    */
   private static String encode(final String command) {
      try {
         return URLEncoder.encode(command, StandardCharsets.UTF_8.name());
      } catch (UnsupportedEncodingException e) {
         throw new IllegalStateException("Every JRE must support UTF-8", e);
      }
   }
}